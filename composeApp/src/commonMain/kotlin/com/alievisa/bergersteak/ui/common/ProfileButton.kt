package com.alievisa.bergersteak.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alievisa.bergersteak.ui.theme.AppDefaults
import com.alievisa.bergersteak.ui.utils.ScaleIndication
import com.alievisa.bergersteak.ui.utils.shimmerBackground

@Composable
fun ProfileButton(
    userName: String?,
    isLoading: Boolean = false,
    onClick: () -> Unit,
) {
    val initials = userName?.split(" ")?.let {
        "${it[0][0]}${if (it.size > 1) it[1][0] else ""}"
    } ?: "?"
    Box(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .then(
                if (isLoading) {
                    Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .shimmerBackground()
                } else {
                    Modifier
                        .clickable(
                            indication = ScaleIndication,
                            interactionSource = null,
                        ) {
                            onClick()
                        }
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(brush = AppDefaults.Brushes.verticalBrownDark)
                }
            )
            .border(
                width = 1.dp,
                color = Color.White,
                shape = CircleShape,
            )
            .padding(1.dp)
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = CircleShape,
            ),
        contentAlignment = Alignment.Center
    ) {
        if (!isLoading) {
            Text(
                text = initials,
                color = Color.White,
                fontSize = 16.sp,
                lineHeight = 16.sp,
            )
        }
    }
}