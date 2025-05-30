package com.alievisa.bergersteak.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

object AppDefaults {

    object Colors {
        val brown = Color(0xFFC67C4E)
        val gray = Color(0xFFEDEDED)
    }

    object Brushes {
        val verticalDark = Brush.verticalGradient(
            colors = listOf(Color(0xFF000000), Color(0xFF2B2B2B)),
        )
        val verticalBrownDark = Brush.verticalGradient(
            colors = listOf(Color(0xFFC67C4E), Color(0xFF603C26))
        )
        val verticalBrownLight = Brush.verticalGradient(
            colors = listOf(Color(0xFFC67C4E), Color(0xFFC67C4E))
        )
    }

    object Icons {
        val remove : ImageVector
            get() = removeIcon ?: createRemoveIcon().also { removeIcon = it }
    }
}

private var removeIcon: ImageVector? = null

private fun createRemoveIcon(): ImageVector {
    return ImageVector.Builder(
        name = "Remove", defaultWidth = 24.dp, defaultHeight = 24.dp,
        viewportWidth = 24f, viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(19f, 13f)
            horizontalLineTo(5f)
            verticalLineTo(11f)
            horizontalLineTo(19f)
            verticalLineTo(13f)
            close()
        }
    }.build()
}