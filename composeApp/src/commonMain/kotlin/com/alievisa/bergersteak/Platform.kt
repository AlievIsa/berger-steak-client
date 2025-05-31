package com.alievisa.bergersteak

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

interface Platform {
    val name: String
}

expect fun getLocalBaseUrl(): String

expect fun getPlatform(): Platform

@Composable
expect fun getInsetTop(): Dp

@Composable
expect fun getInsetBottom(): Dp

@Composable
expect fun getScreenWidth(): Dp

@Composable
expect fun getScreenHeight(): Dp