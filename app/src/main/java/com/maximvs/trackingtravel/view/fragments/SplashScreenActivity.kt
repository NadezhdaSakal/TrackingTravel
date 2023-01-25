package com.maximvs.trackingtravel.view.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.maximvs.trackingtravel.Constants
import com.maximvs.trackingtravel.R
import com.maximvs.trackingtravel.view.MainActivity


class SplashScreenActivity : AppCompatActivity() {
    private var prefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prefs = getSharedPreferences("com.maximvs.trackingtravel", MODE_PRIVATE)

        makeFullScreen()

        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            if (prefs?.getBoolean("firstrun", true) == true) {
                // Do first run stuff here then set 'firstrun' as false
                // start  DataActivity because its your app first run
                // using the following line to edit/commit prefs
                prefs?.edit()?.putBoolean("firstrun", false)?.apply()
                startActivity(Intent(this, StartActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }},Constants.SPLASH_SCREEN_TIMING.toLong() )
    }

    @Suppress("DEPRECATION")
    private fun makeFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar?.hide()
    }
}
