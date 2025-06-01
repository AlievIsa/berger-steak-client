package com.alievisa.bergersteak.ui.sheets.orderinfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import berger_steak_client.composeapp.generated.resources.Res
import berger_steak_client.composeapp.generated.resources.address
import berger_steak_client.composeapp.generated.resources.amount_short
import berger_steak_client.composeapp.generated.resources.date
import berger_steak_client.composeapp.generated.resources.dish
import berger_steak_client.composeapp.generated.resources.order_amount
import berger_steak_client.composeapp.generated.resources.repeat_order
import com.alievisa.bergersteak.domain.models.OrderModel
import com.alievisa.bergersteak.domain.models.OrderStatus.Companion.getStatusStringRes
import com.alievisa.bergersteak.domain.models.findRestaurantById
import com.alievisa.bergersteak.domain.models.mockRestaurants
import com.alievisa.bergersteak.ui.common.MainButton
import com.alievisa.bergersteak.ui.utils.extensions.formatAsReadableDate
import com.alievisa.bergersteak.ui.utils.extensions.rub
import org.jetbrains.compose.resources.stringResource

@Composable
fun OrderInfoScreen(
    orderModel: OrderModel,
    showInBottomSheet: Boolean = false,
) {
    val mainModifier = if (showInBottomSheet) {
        Modifier.wrapContentHeight().fillMaxWidth()
    } else {
        Modifier.fillMaxSize()
    }

    Column(
        modifier = mainModifier
            .padding(horizontal = 20.dp)
            .padding(top = 24.dp),
    ) {

        Text(
            text = stringResource(orderModel.status.getStatusStringRes()),
            fontSize = 20.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(Res.string.dish),
                fontSize = 16.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                modifier = Modifier.weight(4f),
            )
            Text(
                text = stringResource(Res.string.amount_short),
                fontSize = 16.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
            )
        }

        orderModel.positions.forEach { positionModel ->
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                val dishModel = positionModel.dishModel
                Text(
                    text = dishModel.name,
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1,
                    modifier = Modifier.weight(4f),
                )
                Text(
                    text = positionModel.dishAmount.toString(),
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                )
            }
        }

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        Text(
            text = "${stringResource(Res.string.address)}: ${mockRestaurants.findRestaurantById(orderModel.restaurantId)?.address}",
            color = Color.Gray,
            fontSize = 14.sp,
            lineHeight = 14.sp,
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "${stringResource(Res.string.date)}: ${orderModel.timestamp.formatAsReadableDate()}",
            color = Color.Gray,
            fontSize = 14.sp,
            lineHeight = 14.sp,
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text ="${stringResource(Res.string.order_amount)}: ${orderModel.price.rub()}",
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 16.sp,
        )

        Spacer(modifier = Modifier.height(20.dp))

        MainButton(
            modifier = Modifier
                .padding(bottom = 12.dp),
            centerText = stringResource(Res.string.repeat_order),
            onClick = {

            }
        )
    }
}