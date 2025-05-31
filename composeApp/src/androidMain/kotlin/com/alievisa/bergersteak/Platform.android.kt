package com.alievisa.bergersteak

import android.os.Build
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getLocalBaseUrl() = "http://10.0.2.2:8080"

actual fun getPlatform(): Platform = AndroidPlatform()

@Composable
actual fun getInsetTop(): Dp {
    val insets = WindowInsets.systemBars.asPaddingValues()
    return insets.calculateTopPadding()
}

@Composable
actual fun getInsetBottom(): Dp {
    val insets = WindowInsets.systemBars.asPaddingValues()
    return insets.calculateBottomPadding()
}

@Composable
actual fun getScreenWidth(): Dp = LocalConfiguration.current.screenWidthDp.dp

@Composable
actual fun getScreenHeight(): Dp = LocalConfiguration.current.screenHeightDp.dp