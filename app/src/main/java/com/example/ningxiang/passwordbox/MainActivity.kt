package com.example.ningxiang.passwordbox

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Example of a call to a native method
        sample_text.text = stringFromJNI()

        val key = "askdjiofv"
        val content = "This is a 加密 content!"

        val encrypt = AesEncrypt.encryptBase64(key, content.toByteArray())
        Log.e("Main", "content: " + encrypt)

        Log.e("Main", "right key: " + String(AesEncrypt.decrptBase64(key, encrypt)!!))

        Log.e("Main", "error key: " + String(AesEncrypt.decrptBase64("asdaf", encrypt)))

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
