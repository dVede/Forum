package com.example.forum.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.asLiveData
import com.example.forum.R
import com.example.forum.UserPreferences

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { true }
        super.onCreate(savedInstanceState)
        val userPreferences = UserPreferences(this)
        userPreferences.authToken.asLiveData().observe(this) {
            var intent = Intent(this, AuthActivity::class.java)
            if (it != null) {
                intent = Intent(this, MainMenuActivity::class.java)
            }
            startActivity(intent)
            finish()
        }
    }
}