package com.alievisa.bergersteak.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.alievisa.bergersteak.domain.models.PositionModel
import com.alievisa.bergersteak.ui.theme.AppDefaults
import com.alievisa.bergersteak.ui.utils.ScaleIndication
import com.alievisa.bergersteak.ui.utils.extensions.gram
import com.alievisa.bergersteak.ui.utils.extensions.rub

@Composable
fun BasketItem(
    positionModel: PositionModel,
    onIncreaseAmountClick: () -> Unit,
    onDecreaseAmountClick: () -> Unit,
    onDishClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val dishModel = positionModel.dishModel

    Row (
        modifier = modifier
            .clickable(
                indication = ScaleIndication,
                interactionSource = null,
            ) {
                onDishClick()
            }
            .padding(vertical = 4.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(AppDefaults.Colors.gray)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = dishModel.image,
            contentDescription = dishModel.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(75.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.width(22.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {

            Text(
                text = dishModel.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Row(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = dishModel.price.rub(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f),
                    )

                    dishModel.weight?.let {
                        Text(
                            text = dishModel.weight.gram(),
                            fontSize = 14.sp,
                            color = Color.Gray,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f),
                        )
                    }
                }

                QuantitySelector(
                    value = positionModel.dishAmount,
                    onValueChange = { newAmount ->
                        if (newAmount > positionModel.dishAmount) {
                            onIncreaseAmountClick()
                        } else if (newAmount < positionModel.dishAmount) {
                            onDecreaseAmountClick()
                        }
                    },
                )
            }
        }
    }
}