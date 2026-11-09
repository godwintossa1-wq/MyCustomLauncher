package com.custom.launcher

import android.graphics.drawable.Drawable

data class AppItem(
    val appName: String,
    val packageName: String,
    val icon: Drawable?
)
