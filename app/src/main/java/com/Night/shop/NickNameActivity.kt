package com.Night.shop

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_nick_name.*

class NickNameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nick_name)

        done.setOnClickListener{
            //TODO: nickname
            getSharedPreferences("shop",Context.MODE_PRIVATE)
                .edit()
                .putString("NICKNAME",nickname.text.toString())
                .apply()
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}
