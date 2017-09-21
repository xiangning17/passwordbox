package component

/**
 * Created by ningxiang on 17-8-29.
 */
object PasswordManager {

    private var itemManager: ItemManager? = null

    private var rootKey: String? = null


    /**
     * 判断是否已经为密码管理器设置了根密码（rootKey）
     *
     * @return true为已经设置了根密码，false反之
     */
    fun hasRootKey() = ItemManager.hasRootItem()

    fun setRootKey(oldKey: String?, newKey: String): Boolean {

        if (!hasRootKey()) {
            itemManager = ItemManager(newKey)
        } else {
            //若之前已设置过rootKey，则需要验证oldKey
            //验证不通过，返回false，成功则继续后面的流程
            if (!oldKey.isNullOrEmpty() && !open(oldKey!!)) {
                return false
            }
        }

        itemManager?.changeRootKey(newKey)

        //改变根密码
        this.rootKey = newKey

        return true
    }

    /**
     * 判断自从进程开始以来是否已经从用户输入得到过正确的根密码（rootKey）
     *
     * @return true表明用户已经在有过一次正确输入根密码并被保存到了内存中， false反之
     */
    fun isOpend() = rootKey != null

    /**
     * 当使用'isOpen'判断返回false时，需要通过此方法提供根密码去打开（open）密码管理器
     *
     * @param rootKey 提供根密码进行验证以打开密码管理器
     *
     * @return 打开成功返回true， 根密码错误返回false
     */
     fun open(rootKey: String): Boolean {
        if (hasRootKey()) {
            if (!isOpend()) {

                //解密成功即表明根密码正确，否则密码错误，返回false
                try {
                    itemManager = ItemManager(rootKey)
                } catch (e: Exception) {
                    e.printStackTrace()
                    return false
                }

                this.rootKey = rootKey
                return true
            } else {
                return this.rootKey == rootKey
            }
        } else{
            //还未设置根密码的，在此处以参数提供的rootKey作为根密码
            //因为之前未设置rootKey，因此第一个参数给‘null’或任意值，不会验证
            return setRootKey(null, rootKey)
        }
    }

    fun getRootKey() = rootKey

    fun getAllItems() = itemManager?.getAll()?.values?.toList()?: ArrayList()

    fun getAllIds() = itemManager?.getAll()?.keys?.toList()?: ArrayList()

    fun getItemById(id: Int) = itemManager?.getItem(id)

    fun addItem(name: String, account: String, pwd: String)
            = isOpend() && itemManager!!.putItem(name, account, pwd) > 0
}