package com.maximvs.trackingtravel

import android.app.Application
import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager
//import androidx.preference.PreferenceManager
import com.maximvs.trackingtravel.view.MainActivity
import com.maximvs.trackingtravel.view.fragments.StartActivity
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        val userInfo: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(
            applicationContext
        )
        val editor = userInfo.edit()
        val logedIn = userInfo.getBoolean("loggedIn", false)
        if (logedIn) {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        } else {
            val intent = Intent(this, StartActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    companion object {
        lateinit var instance: App
            private set
    }
}



