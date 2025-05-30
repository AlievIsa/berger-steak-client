package com.alievisa.bergersteak.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.alievisa.bergersteak.utils.ScaleIndication
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun ToolbarButton(
    icon: ImageVector,
    contentDescription: String? = null,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .clickable(
                indication = ScaleIndication,
                interactionSource = null,
            ) {
                onClick()
            }
            .padding(horizontal = 20.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = Color.Unspecified,
        )
    }
}