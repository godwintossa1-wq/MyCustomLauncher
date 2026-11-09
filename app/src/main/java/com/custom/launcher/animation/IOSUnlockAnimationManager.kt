package com.custom.launcher.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

object IOSUnlockAnimationManager {

    /**
     * Plays an iOS-style smooth scale and fade-in unlock animation on the launcher workspace.
     */
    fun playIOSUnlockAnimation(workspaceRoot: View, onComplete: (() -> Unit)? = null) {
        // 1. Set initial hidden state (slightly scaled down and fully transparent)
        workspaceRoot.scaleX = 0.92f
        workspaceRoot.scaleY = 0.92f
        workspaceRoot.alpha = 0f
        workspaceRoot.visibility = View.VISIBLE

        // 2. Animate smoothly back to full size and opacity
        workspaceRoot.animate()
            .scaleX(1.0f)
            .scaleY(1.0f)
            .alpha(1.0f)
            .setDuration(400L) // Premium weighted duration
            .setInterpolator(FastOutSlowInInterpolator()) // Smooth deceleration curve
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    onComplete?.invoke()
                }
            })
            .start()
    }
}
