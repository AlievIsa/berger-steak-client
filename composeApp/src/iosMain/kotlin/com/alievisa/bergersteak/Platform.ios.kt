package com.alievisa.bergersteak

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.UIKit.UIApplication
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

@Composable
@OptIn(ExperimentalForeignApi::class)
actual fun getInsetTop(): Dp {
    val window = UIApplication.sharedApplication.keyWindow ?: return 0.dp
    return window.safeAreaInsets.useContents { top }.toFloat().dp
}

@Composable
@OptIn(ExperimentalForeignApi::class)
actual fun getInsetBottom(): Dp {
    val window = UIApplication.sharedApplication.keyWindow ?: return 0.dp
    return window.safeAreaInsets.useContents { bottom }.toFloat().dp
}

@Composable
@OptIn(ExperimentalComposeUiApi::class)
actual fun getScreenWidth(): Dp = LocalWindowInfo.current.containerSize.width.dp

@Composable
@OptIn(ExperimentalComposeUiApi::class)
actual fun getScreenHeight(): Dp = LocalWindowInfo.current.containerSize.height.dp