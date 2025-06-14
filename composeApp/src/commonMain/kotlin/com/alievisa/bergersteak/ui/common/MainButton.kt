package com.alievisa.bergersteak.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alievisa.bergersteak.ui.theme.AppDefaults
import com.alievisa.bergersteak.ui.utils.ScaleIndication
import com.alievisa.bergersteak.ui.utils.shimmerBackground

@Composable
fun MainButton(
    centerText: String,
    onClick: () -> Unit,
    leftIcon: ImageVector? = null,
    rightText: String? = null,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .then(
                if (!isLoading) {
                    Modifier
                        .clickable(
                            interactionSource = null,
                            indication = ScaleIndication,
                            onClick = onClick
                        )
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            brush = AppDefaults.Brushes.verticalBrownLight
                        )
                } else {
                    Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .shimmerBackground(
                            mainColor = Color.Gray,
                        )
                }
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
            leftIcon?.let {
                Icon(
                    imageVector = leftIcon,
                    contentDescription = null,
                    tint = Color.Unspecified,
                )
            }
        }
        Box(modifier = Modifier.weight(3f), contentAlignment = Alignment.Center) {
            Text(
                text = centerText,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
            rightText?.let {
                Text(
                    text = rightText,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}