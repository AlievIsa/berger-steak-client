package com.alievisa.bergersteak.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alievisa.bergersteak.getInsetTop
import com.alievisa.bergersteak.ui.theme.AppDefaults

@Composable
fun Toolbar(
    title: String? = null,
    leftPart: (@Composable () -> Unit)? = null,
    rightPart: (@Composable () -> Unit)? = null,
    isTransparent: Boolean = false,
    modifier: Modifier = Modifier
) {


    val transparentBrush =  Brush.verticalGradient(
        colors = listOf(Color.Transparent, Color.Transparent),
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(brush = if (!isTransparent) AppDefaults.Brushes.verticalDark else transparentBrush),
    ) {
        Spacer(modifier = Modifier.fillMaxWidth().height(getInsetTop()))
        Row(
            modifier = Modifier.fillMaxWidth().height(48.dp).padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                leftPart?.invoke()
            }
            Box(modifier = Modifier.weight(2f), contentAlignment = Alignment.Center) {
                title?.let {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
                rightPart?.invoke()
            }
        }
    }
}