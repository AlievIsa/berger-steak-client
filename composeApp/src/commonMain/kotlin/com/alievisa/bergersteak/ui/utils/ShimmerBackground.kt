package com.alievisa.bergersteak.ui.utils

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import com.alievisa.bergersteak.getScreenWidth

fun Modifier.shimmerBackground(
    mainColor: Color = Color.LightGray,
    animationColor: Color = Color.White,
    widthOfShadowBrush: Int = 300,
    endOfOffsetY: Float = 120f,
    durationMillis: Int = 1000,
): Modifier = composed {
    val shimmerColors = listOf(
        mainColor,
        mainColor,
        animationColor.copy(alpha = 0.5f),
        animationColor,
        animationColor.copy(alpha = 0.5f),
        mainColor,
        mainColor,
    )

    val transition = rememberInfiniteTransition(label = "shimmerLoadingAnimation")

    val density = LocalDensity.current
    val width = with(density) { getScreenWidth().toPx() }

    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = width + 100f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart,
        ),
        label = "Shimmer loading animation",
    )

    this.background(
        brush = Brush.linearGradient(
            colors = shimmerColors,
            start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0.0f),
            end = Offset(x = translateAnimation.value, y = endOfOffsetY),
        ),
    )
}