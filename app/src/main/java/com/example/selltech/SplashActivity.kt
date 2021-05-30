package com.example.selltech

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.TestLooperManager
import android.widget.TextView
import androidx.appcompat.app.ActionBar


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        @Suppress("Depreciation")
        Handler().postDelayed({
             startActivity(Intent(this,MainActivity::class.java))
        finish()},1800)




    }
}