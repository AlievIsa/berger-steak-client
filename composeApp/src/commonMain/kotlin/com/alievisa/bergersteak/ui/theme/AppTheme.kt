package com.alievisa.bergersteak.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = SoraTypography(),
        content = content,
    )
}