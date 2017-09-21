package component

import java.util.*

/**
 * Created by ningxiang on 17-9-1.
 */
internal class ItemManager(key: String, oldKey: String? = null) {

    companion object {
        private val ITEMS_POINTER_KEY = "ITEMS_POINTER"

        private val BASE_SECURITY_ID = 0
        private val ROOT_KEY_ID = BASE_SECURITY_ID
        private val PIN_KEY_ID = BASE_SECURITY_ID + 1

        private val BASE_USER_ID = 100

        inline fun<T> T.doIfNotNull(block: T.() -> Unit): T { this?.block(); return this }

        fun hasRootItem() = ConfigManager.contains(ROOT_KEY_ID)
    }


    private val items = HashMap<Int, Item>()

    private var key = key

    init {

        ConfigManager.getAll().keys
                .mapNotNull { it.toIntOrNull() }
                .filter { it > 0 }
                .forEach { getItem(it) }

        if (!hasRootItem()) { //第一次设置rootkey
            sync(ROOT_KEY_ID, Item("root", "root", key))
        } else {
            if (getRootItem() == null)
                throw RuntimeException("Error root key while load items")
        }

    }

    fun getRootItem() = items[ROOT_KEY_ID]

    fun getPinItem() = items[PIN_KEY_ID]

    fun putItem(name: String, account: String, pwd: String) = putItem(Item(name, account, pwd))

    fun putItem(item: Item): Int {
//        val itemsPointer = ConfigManager.getInt(ITEMS_POINTER_KEY, BASE_USER_ID)
        val itemsPointer = 1 + maxOf(BASE_USER_ID,
                Collections.max(ConfigManager.getAll().keys.mapNotNull { it.toIntOrNull() }))
        return if (sync(itemsPointer, item)/*
            && ConfigManager.put(ITEMS_POINTER_KEY, itemsPointer + 1)*/ ) {
            itemsPointer
        } else {
            -1
        }
    }

    fun updateItem(id: Int, item: Item) = ConfigManager.contains(id)
            && sync(id, item)

    fun getItem(id: Int): Item? {

        return items.getOrElse(id,
                {Item.fromString(
                        EncryptTool.decryptFromBase64(key, ConfigManager.getString(id, "")))
                        .doIfNotNull { this!!.id = id.toString(); items.put(id, this) }} )
    }

    fun removeItem(id: Int) = {items.remove(id); sync(id, null)}

    private fun sync(id: Int, item: Item?): Boolean {
        item.doIfNotNull { this?.id = id.toString(); items.put(id, this!!) } // items.put(id, item)

        return if (item == null) {
            ConfigManager.remove(id)
        } else {
            ConfigManager.put(id,
                    EncryptTool.encryptToBase64(key, item.toString().toByteArray()))
        }
    }

    fun changeRootKey(newKey: String) {
        key = newKey
        items.entries.forEach { updateItem(it.key, it.value) }
    }

    fun getAll() = items.filterKeys { it > BASE_USER_ID }
}