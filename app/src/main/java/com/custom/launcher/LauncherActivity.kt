package com.custom.launcher

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.custom.launcher.animation.IOSUnlockAnimationManager

class LauncherActivity : AppCompatActivity() {

    private lateinit var workspaceRootLayout: View

    private val unlockReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == Intent.ACTION_USER_PRESENT) {
                // Trigger the iOS-style unlock animation when the device unlocks
                IOSUnlockAnimationManager.playIOSUnlockAnimation(workspaceRootLayout)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        // Reference your main workspace container from layout XML
        workspaceRootLayout = findViewById(R.id.workspaceRootLayout)

        // Register receiver for user unlock events
        val filter = IntentFilter(Intent.ACTION_USER_PRESENT)
        registerReceiver(unlockReceiver, filter)
    }

    override fun onResume() {
        super.onResume()
        // Play unlock animation when returning to the home screen
        IOSUnlockAnimationManager.playIOSUnlockAnimation(workspaceRootLayout)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(unlockReceiver)
    }
}
