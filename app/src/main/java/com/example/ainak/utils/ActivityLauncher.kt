package com.example.ainak.utils

import android.app.Activity
import android.content.Intent
import com.example.ainak.ui.home.HomeActivity
import com.example.ainak.ui.splash.SplashActivity

/**
 * Utility class to launch activities.
 */
class ActivityLauncher {

    companion object {

        fun launchSplashActivity(activity: Activity) {
            val intent = Intent(activity, SplashActivity::class.java)
            activity.startActivity(intent)
        }

        fun launchHomeActivity(activity: Activity) {
            val intent = Intent(activity, HomeActivity::class.java)
            activity.startActivity(intent)
        }
    }
}