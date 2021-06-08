package com.hpdev.bangkitcapstone.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.hpdev.bangkitcapstone.R
import com.hpdev.bangkitcapstone.data.UserEntity
import com.hpdev.bangkitcapstone.db.UserHelper
import java.lang.Exception

class SplashScreenActivity : AppCompatActivity() {
    private var splashDelay = 2000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, splashDelay)
    }
}