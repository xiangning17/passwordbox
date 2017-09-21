package component

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by ningxiang on 17-8-30.
 */
internal object ConfigManager {
    private lateinit var sp: SharedPreferences

    fun init(context: Context) {
        sp = context.applicationContext.getSharedPreferences("config", Context.MODE_PRIVATE)
    }
    
    fun put(key: Any, value: Any): Boolean {

        var name = key.toString()

        return when (value) {
            is Int -> sp.edit().putInt(name, value).commit()
            is Boolean -> sp.edit().putBoolean(name, value).commit()
            is Float -> sp.edit().putFloat(name, value).commit()
            is Long -> sp.edit().putLong(name, value).commit()
            is String -> sp.edit().putString(name, value).commit()
            else -> throw RuntimeException("SharedPreference Not support type : ${value.javaClass.simpleName}")
        }
    }

    fun getAll() = sp.all

    fun clear():Boolean {
        return sp.edit().clear().commit()
    }

    fun remove(key: Any): Boolean {
        return sp.edit().remove(key.toString()).commit()
    }


    fun contains(key: Any): Boolean {
        return sp.contains(key.toString())
    }

    fun getBoolean(key: Any, defValue: Boolean): Boolean {
        return sp.getBoolean(key.toString(), defValue)
    }

    fun getInt(key: Any, defValue: Int): Int {
        return sp.getInt(key.toString(), defValue)
    }

    fun getString(key: Any, defValue: String?): String {
        return sp.getString(key.toString(), defValue)
    }

}