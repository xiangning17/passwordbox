package com.example.ningxiang.passwordbox

import android.app.Application
import component.ConfigManager

/**
 * Created by ningxiang on 17-9-6.
 */
class PasswordApplication: Application() {
    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     * Implementations should be as quick as possible (for example using
     * lazy initialization of state) since the time spent in this function
     * directly impacts the performance of starting the first activity,
     * service, or receiver in a process.
     * If you override this method, be sure to call super.onCreate().
     */
    override fun onCreate() {
        super.onCreate()

        ConfigManager.init(this)
    }
}