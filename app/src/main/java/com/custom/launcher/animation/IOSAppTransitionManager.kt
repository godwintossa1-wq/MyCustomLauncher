package com.custom.launcher.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

object IOSAppTransitionManager {

    /**
     * Executes an iOS-style smooth zoom transition from the tapped icon to full screen.
     */
    fun playIOSOpenAnimation(
        clickedIcon: View,
        containerView: ViewGroup,
        onAnimationComplete: () -> Unit
    ) {
        // 1. Get the exact starting bounds of the icon on screen
        val startRect = Rect()
        clickedIcon.getGlobalVisibleRect(startRect)

        // 2. Define target full-screen bounds
        val endRect = Rect(0, 0, containerView.width, containerView.height)

        // 3. Create a temporary overlay view to mimic the expanding app window
        val transitionView = View(clickedIcon.context).apply {
            background = clickedIcon.background 
            elevation = 30f
        }

        val layoutParams = ViewGroup.LayoutParams(
            startRect.width(),
            startRect.height()
        )
        transitionView.x = startRect.left.toFloat()
        transitionView.y = startRect.top.toFloat()

        // Add the transition view to the root overlay layout
        containerView.addView(transitionView, layoutParams)

        // Hide the original icon temporarily for a clean transition effect
        clickedIcon.alpha = 0f

        // 4. Animate position and size using a smooth curve close to iOS timing
        val animator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 380L // Standard fluid iOS app-open duration
            interpolator = FastOutSlowInInterpolator()

            addUpdateListener { anim ->
                val progress = anim.animatedValue as Float

                val currentLeft = startRect.left + (endRect.left - startRect.left) * progress
                val currentTop = startRect.top + (endRect.top - startRect.top) * progress
                val currentWidth = startRect.width() + (endRect.width() - startRect.width()) * progress
                val currentHeight = startRect.height() + (endRect.height() - startRect.height()) * progress

                transitionView.x = currentLeft
                transitionView.y = currentTop

                val lp = transitionView.layoutParams
                lp.width = currentWidth.toInt()
                lp.height = currentHeight.toInt()
                transitionView.layoutParams = lp
            }
        }

        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                containerView.removeView(transitionView)
                clickedIcon.alpha = 1f
                onAnimationComplete()
            }
        })

        animator.start()
    }
}
