package com.alievisa.bergersteak.ui.sheets.dish

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import berger_steak_client.composeapp.generated.resources.Res
import berger_steak_client.composeapp.generated.resources.done
import coil3.compose.AsyncImage
import com.alievisa.bergersteak.domain.models.DishModel
import com.alievisa.bergersteak.ui.common.ExpandableText
import com.alievisa.bergersteak.ui.common.QuantitySelector
import com.alievisa.bergersteak.ui.theme.AppDefaults
import com.alievisa.bergersteak.ui.utils.ScaleIndication
import com.alievisa.bergersteak.ui.utils.extensions.cal
import com.alievisa.bergersteak.ui.utils.extensions.gram
import com.alievisa.bergersteak.ui.utils.extensions.rub
import org.jetbrains.compose.resources.stringResource

@Composable
fun DishContent(
    dishModel: DishModel,
    initialAmount: Int = 0,
    showInBottomSheet: Boolean = false,
    onDoneClick: (Int) -> Unit,
) {
    val mainModifier = if (showInBottomSheet) {
        Modifier.wrapContentHeight().fillMaxWidth()
    } else {
        Modifier.fillMaxSize()
    }
    var currentAmount by remember { mutableStateOf(initialAmount) }
    val currentPrice = currentAmount * dishModel.price
    val hasPrice = currentPrice > 0
    val paddingEnd by animateDpAsState(
        targetValue = if (hasPrice) 50.dp else 0.dp,
        label = "DoneTextOffset"
    )

    Box(
        modifier = mainModifier
    ) {
        AsyncImage(
            model = dishModel.image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            alignment = Alignment.BottomCenter,
        )

        Column(
            modifier = Modifier
                .padding(top = 270.dp)
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            Text(
                text = dishModel.name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black,
                modifier = Modifier.padding(top = 4.dp)
            )

            dishModel.description?.let {
                ExpandableText(
                    text = dishModel.description,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Divider(modifier = Modifier.padding(vertical = 10.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = dishModel.price.rub(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                dishModel.weight?.let {
                    Text(
                        text = dishModel.weight.gram(),
                        fontSize = 16.sp,
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(end = 12.dp)
                    )
                }

                dishModel.calories?.let {
                    Text(
                        text = dishModel.calories.cal(),
                        fontSize = 16.sp,
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                QuantitySelector(
                    value = currentAmount,
                    onValueChange = {
                        currentAmount = it
                    },
                )

                Box(
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .height(40.dp)
                        .weight(1f)
                        .clickable(
                            indication = ScaleIndication,
                            interactionSource = null
                        ) {
                            onDoneClick(currentAmount)
                        }
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            brush = AppDefaults.Brushes.verticalBrownLight
                        ),
                ) {

                    Text(
                        text = stringResource(Res.string.done),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        lineHeight = 18.sp,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.align(Alignment.Center).padding(end = paddingEnd)
                    )

                    if (currentPrice > 0) {
                        Text(
                            text = currentPrice.rub(),
                            fontSize = 14.sp,
                            lineHeight = 16.sp,
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.align(Alignment.CenterEnd).padding(end = 20.dp)
                        )
                    }
                }
            }
        }
    }
}