package com.example.gogreen.ui.splashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.gogreen.MainActivity
import com.example.gogreen.R
import com.example.gogreen.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    companion object {
        private const val TWO_SECOND_IN_MILLIS = 5000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, TWO_SECOND_IN_MILLIS)
    }
}