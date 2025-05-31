package com.alievisa.bergersteak.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.alievisa.bergersteak.domain.models.DishModel
import com.alievisa.bergersteak.domain.models.OrderModel
import com.alievisa.bergersteak.getScreenHeight
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DraggableBottomSheet(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit,
) {
    val density = LocalDensity.current
    val swipeableState = rememberSwipeableState(initialValue = if (isVisible) 0 else 1)
    val screenHeightPx = with(density) { getScreenHeight().toPx() }
    val anchors = mapOf(0f to 0, screenHeightPx to 1)
    val scope = rememberCoroutineScope()
    val overlayAlpha by remember {
        derivedStateOf {
            val progress = swipeableState.offset.value / screenHeightPx
            0.5f * (1f - progress.coerceIn(0f, 1f))
        }
    }

    LaunchedEffect(isVisible) {
        if (isVisible) {
            swipeableState.animateTo(0)
        } else {
            swipeableState.animateTo(1)
        }
    }

    LaunchedEffect(swipeableState.currentValue) {
        if (swipeableState.currentValue == 1 && isVisible) {
            onDismiss()
        }
    }

    if (isVisible || swipeableState.currentValue == 0) {

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = overlayAlpha))
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                    ) {
                        scope.launch {
                            swipeableState.animateTo(1)
                        }
                    }
            )

            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .swipeable(
                        state = swipeableState,
                        anchors = anchors,
                        orientation = Orientation.Vertical,
                        resistance = null
                    )
                    .offset { IntOffset(0, swipeableState.offset.value.toInt()) }
                    .align(Alignment.BottomCenter),
            ) {
                Surface(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)),
                    color = MaterialTheme.colors.surface,
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        content()
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(top = 8.dp)
                                .width(36.dp)
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(Color.DarkGray)
                        )
                    }
                }
            }
        }
    }
}

sealed class BottomSheetContent {
    data class Dish(val dishModel: DishModel) : BottomSheetContent()

    data object AboutUs : BottomSheetContent()

    data object Authorization : BottomSheetContent()

    data object OrderDetails : BottomSheetContent()

    data class OrderInfo(val orderModel: OrderModel) : BottomSheetContent()
}