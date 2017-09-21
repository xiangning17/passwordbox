package com.example.ningxiang.passwordbox

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import component.PasswordManager
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        if (!PasswordManager.hasRootKey()) {
            textView2.text = "设置密码"
        }

        button.setOnClickListener {
            var result = PasswordManager.open(editText.text.trim().toString())
            setResult(if (result) Activity.RESULT_OK else Activity.RESULT_CANCELED)
            Toast.makeText(this, "password correct? $result", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}
