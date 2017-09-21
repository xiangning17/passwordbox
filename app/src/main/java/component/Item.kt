package component

import android.text.TextUtils
import android.util.Log

/**
 * Created by ningxiang on 17-9-1.
 */
data class Item(var name: String, var account: String, var pwd: String, var id:String = "-1") {
    /**
     * Returns a string representation of the object.
     */
    override fun toString(): String {
        return TextUtils.join(DELIMITER, listOf(name, account, pwd))
    }

    companion object {
        val DELIMITER = "\u0000"

        fun fromString(value: String): Item? {
            var fileds = value.split(DELIMITER, ignoreCase = false)
            Log.e("NingXiangDebug", "value are: $value")
            Log.e("NingXiangDebug", "fileds are: $fileds")
            if (fileds.size != 3)
                return null
            return Item(fileds[0], fileds[1], fileds[2])
        }
    }
}