package com.alievisa.bergersteak.ui.screens.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Phone
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
import androidx.navigation.NavController
import berger_steak_client.composeapp.generated.resources.Res
import berger_steak_client.composeapp.generated.resources.arrow_back
import berger_steak_client.composeapp.generated.resources.average_receipt
import berger_steak_client.composeapp.generated.resources.bag_check
import berger_steak_client.composeapp.generated.resources.date
import berger_steak_client.composeapp.generated.resources.exit
import berger_steak_client.composeapp.generated.resources.number_of_orders
import berger_steak_client.composeapp.generated.resources.order_amount
import berger_steak_client.composeapp.generated.resources.order_history
import berger_steak_client.composeapp.generated.resources.profile
import com.alievisa.bergersteak.domain.models.MockUser
import com.alievisa.bergersteak.ui.common.BottomSheetContent
import com.alievisa.bergersteak.ui.common.DraggableBottomSheet
import com.alievisa.bergersteak.ui.common.MainButton
import com.alievisa.bergersteak.ui.common.Toolbar
import com.alievisa.bergersteak.ui.common.ToolbarButton
import com.alievisa.bergersteak.ui.sheets.OrderInfoScreen
import com.alievisa.bergersteak.ui.theme.AppDefaults
import com.alievisa.bergersteak.ui.utils.ScaleIndication
import com.alievisa.bergersteak.ui.utils.extensions.formatAsReadableDate
import com.alievisa.bergersteak.ui.utils.extensions.rub
import com.alievisa.bergersteak.ui.utils.extensions.toPhoneNumberFormat
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun ProfileScreen(navController: NavController) {
    val userModel = MockUser.data
    val orders = userModel.orders
    val totalOrders = userModel.orders.size
    val averageCheck = if (orders.isNotEmpty()) orders.sumOf { it.price } / orders.size else 0
    val initials =
        userModel.name.split(" ").let { "${it[0][0]}${if (it.size > 1) it[1][0] else ""}" }
    var currentBottomSheetContent by remember { mutableStateOf<BottomSheetContent?>(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Toolbar(
            title = stringResource(Res.string.profile),
            leftPart = {
                ToolbarButton(
                    icon = vectorResource(Res.drawable.arrow_back),
                    contentDescription = "Arrow Back",
                    onClick = {
                        navController.popBackStack()
                    }
                )
            },
        )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F8F8))
                .padding(20.dp)
                .weight(1f)
        ) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = 4.dp
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                brush = AppDefaults.Brushes.verticalBrownDark
                            )
                            .border(
                                width = 1.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(12.dp),
                            )
                            .padding(1.dp)
                            .border(
                                width = 1.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(12.dp),
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = initials,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = userModel.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )

                        Row {
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(18.dp).align(Alignment.CenterVertically),
                            )

                            Text(
                                text = userModel.phoneNumber.toPhoneNumberFormat(),
                                fontSize = 14.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(start = 4.dp),
                            )
                        }

                        Row {
                            Icon(
                                imageVector = Icons.Default.MailOutline,
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(18.dp).align(Alignment.CenterVertically),
                            )

                            Text(
                                text = userModel.mail,
                                fontSize = 14.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(start = 4.dp),
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.Top)
                            .clickable(
                                indication = ScaleIndication,
                                interactionSource = null,
                            ) {

                            }
                            .border(
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = Color.Gray,
                                ),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(4.dp)

                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Icon",
                            tint = Color.Gray,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = 2.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "${stringResource(Res.string.number_of_orders)}: $totalOrders",
                        fontWeight = FontWeight.Medium
                    )

                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(
                        text = "${stringResource(Res.string.average_receipt)}: ${averageCheck.rub()}",
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = 2.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(Res.string.order_history),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    LazyColumn {
                        items(orders) { order ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(
                                        indication = ScaleIndication,
                                        interactionSource = null,
                                    ) {
                                        currentBottomSheetContent = BottomSheetContent.OrderInfo(order)
                                    }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = vectorResource(Res.drawable.bag_check),
                                        contentDescription = null,
                                        tint = Color.Gray
                                    )

                                    Spacer(modifier = Modifier.width(24.dp))

                                    Column {
                                        Text(
                                            text ="${stringResource(Res.string.order_amount)}: ${order.price.rub()}",
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 14.sp,
                                            lineHeight = 14.sp,
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = "${stringResource(Res.string.date)}: ${order.timestamp.formatAsReadableDate()}",
                                            color = Color.Gray,
                                            fontSize = 14.sp,
                                            lineHeight = 14.sp,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        MainButton(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(bottom = 12.dp),
            centerText = stringResource(Res.string.exit),
            onClick = {
                MockUser.isAuthorized = false
                navController.popBackStack()
            }
        )
    }

    DraggableBottomSheet(
        isVisible = currentBottomSheetContent != null,
        onDismiss = { currentBottomSheetContent = null }
    ) {
        when (val content = currentBottomSheetContent) {
            is BottomSheetContent.OrderInfo -> {
                OrderInfoScreen(
                    orderModel = content.orderModel,
                    showInBottomSheet = true,
                )
            }
            else -> {}
        }
    }
}