package com.example.ningxiang.passwordbox

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * Created by ningxiang on 17-8-21.
 */
object AuthenService : Service() {

    private var mPwd : String? = null
    private var mPin : String? = null

    override fun onBind(intent: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    /**
     * 初始化pwd和PIN(若使能了的话)
     */
    fun init(pwd: String?, pin: String? = null) {

        if (pwd.isNullOrEmpty()) {
            throw RuntimeException("password can not be empty!")
        }

        mPwd = pwd

        if (pinEnabled() && pin.isNullOrEmpty()) {
            throw RuntimeException("PIN can not be empty!")
        }

        mPin = pin
    }

    /**
     * 判断密码是否已经初始化过了
     */
    fun isInited() = !mPwd.isNullOrEmpty() && (!pinEnabled() || !mPin.isNullOrEmpty())


    fun pinEnabled() : Boolean {
        return false
    }

    fun pwdAuthen(pwd: String) : String {
        if (pwd != mPwd) {
            throwAuthenFailureError("pwd not correct.")
        }

        return if (pinEnabled()) (pwd + mPin) else pwd
    }

    fun pinAuthen(pin: String) : String {
        if (pin != mPin) {
            throwAuthenFailureError("PIN not correct.")
        }

        return mPwd + pin
    }

    fun fingerAuthen() {

    }

    private fun throwAuthenFailureError(msg: String?) {
        throw RuntimeException("Authen Failed! $msg")
    }
}