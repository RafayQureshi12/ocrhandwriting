// SplashActivity.kt
package com.example.ocrhandwriting

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.madproject.R.*
import com.example.madproject.R.id.lottie_splash

class SplashActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_splash)

        val animationView: LottieAnimationView = findViewById(lottie_splash)
        animationView.setAnimation(raw.explore)  // Use your JSON file name here
        animationView.playAnimation()

        // Duration of the splash screen
        val SPLASH_SCREEN_DURATION = 3000L // 3 seconds

        Handler().postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_SCREEN_DURATION)
    }
}
