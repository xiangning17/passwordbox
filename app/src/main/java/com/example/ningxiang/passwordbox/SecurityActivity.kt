package com.example.ningxiang.passwordbox

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

open class SecurityActivity : AppCompatActivity() {

    private var verified = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onStart() {
        super.onStart()
        if (!verified) {
            startActivityForResult(Intent(this, AuthActivity::class.java), 1)
        }
    }

    override fun onStop() {
        super.onStop()
        verified = false
    }

    /**
     * Dispatch incoming result to the correct fragment.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                verified = true
            } else {
                finish()
            }
        }
    }
}
