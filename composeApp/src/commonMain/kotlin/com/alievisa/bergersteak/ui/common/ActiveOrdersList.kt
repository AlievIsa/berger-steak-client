package com.alievisa.bergersteak.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import berger_steak_client.composeapp.generated.resources.Res
import berger_steak_client.composeapp.generated.resources.active_orders
import berger_steak_client.composeapp.generated.resources.done
import berger_steak_client.composeapp.generated.resources.order_canceled
import berger_steak_client.composeapp.generated.resources.order_created
import berger_steak_client.composeapp.generated.resources.order_done
import berger_steak_client.composeapp.generated.resources.order_processing
import com.alievisa.bergersteak.data.OrderModel
import com.alievisa.bergersteak.data.OrderStatus
import com.alievisa.bergersteak.ui.theme.AppDefaults
import com.alievisa.bergersteak.utils.ScaleIndication
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun ActiveOrdersList(
    orders: List<OrderModel>,
    onOrderClick: (OrderModel) -> Unit,
) {
    val activeOrders = orders.filter { it.status != OrderStatus.RECEIVED }
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { activeOrders.size })
    var expanded by remember { mutableStateOf(true) }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 10.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .clickable(indication = ScaleIndication, interactionSource = null) {
                    expanded = !expanded
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                contentDescription = null,
                tint = Color.Gray
            )
            Text(
                text = stringResource(Res.string.active_orders),
                fontSize = 18.sp,
                lineHeight = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                modifier = Modifier.weight(1f),
            )
            if (pagerState.pageCount > 1 && expanded) {
                Text(
                    text = "${pagerState.currentPage + 1}/${pagerState.pageCount}",
                    fontSize = 18.sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                )
            }
        }

        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalPager(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth(),
                state = pagerState
            ) { pageIndex ->
                ActiveOrderItem(
                    orderModel = activeOrders[pageIndex],
                    onOrderClick = onOrderClick,
                )
            }
        }
    }
}

@Composable
fun ActiveOrderItem(
    orderModel: OrderModel,
    onOrderClick: (OrderModel) -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
            .clickable(
                indication = ScaleIndication, interactionSource = null
            ) {
                onOrderClick(orderModel)
            }
            .clip(RoundedCornerShape(8.dp))
            .background(AppDefaults.Colors.gray)
            .padding(8.dp),
    ) {
        val tintsState = when (orderModel.status) {
            OrderStatus.CREATED -> listOf(true, false, false)
            OrderStatus.PROCESSING -> listOf(true, true, false)
            OrderStatus.CANCELED,
            OrderStatus.DONE,
            OrderStatus.RECEIVED -> listOf(true, true, true)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = vectorResource(Res.drawable.order_created),
                        contentDescription = null,
                        tint = if (tintsState[0]) AppDefaults.Colors.brown else Color.Gray,
                    )
                    Divider(
                        modifier = Modifier
                            .height(2.dp)
                            .weight(1f)
                            .background(if (tintsState[1]) AppDefaults.Colors.brown else Color.Gray)
                    )
                }
                Text(
                    text = stringResource(Res.string.order_created),
                    fontSize = 12.sp,
                    lineHeight = 12.sp,
                    color = if (tintsState[0]) AppDefaults.Colors.brown else Color.Gray,
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Divider(
                        modifier = Modifier
                            .height(2.dp)
                            .weight(1f)
                            .background(if (tintsState[1]) AppDefaults.Colors.brown else Color.Gray)
                    )
                    Icon(
                        imageVector = vectorResource(Res.drawable.order_processing),
                        contentDescription = null,
                        tint = if (tintsState[1]) AppDefaults.Colors.brown else Color.Gray,
                    )
                    Divider(
                        modifier = Modifier
                            .height(2.dp)
                            .weight(1f)
                            .background(if (tintsState[2]) AppDefaults.Colors.brown else Color.Gray)
                    )
                }
                Text(
                    text = stringResource(Res.string.order_processing),
                    fontSize = 12.sp,
                    lineHeight = 12.sp,
                    color = if (tintsState[1]) AppDefaults.Colors.brown else Color.Gray,
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Divider(
                        modifier = Modifier
                            .height(2.dp)
                            .weight(1f)
                            .background(if (tintsState[2]) AppDefaults.Colors.brown else Color.Gray)
                    )
                    Icon(
                        imageVector = if (orderModel.status == OrderStatus.CANCELED) {
                            vectorResource(Res.drawable.order_canceled)
                        } else {
                            vectorResource(Res.drawable.order_done)
                        },
                        contentDescription = null,
                        tint = if (tintsState[2]) AppDefaults.Colors.brown else Color.Gray,
                    )
                    Spacer(
                        modifier = Modifier
                            .weight(1f)
                    )
                }
                Text(
                    text = if (orderModel.status == OrderStatus.CANCELED) {
                        stringResource(Res.string.order_canceled)
                    } else {
                        stringResource(Res.string.order_done)
                    },
                    fontSize = 12.sp,
                    lineHeight = 12.sp,
                    color = if (tintsState[2]) AppDefaults.Colors.brown else Color.Gray,
                )
            }
        }
    }
}
