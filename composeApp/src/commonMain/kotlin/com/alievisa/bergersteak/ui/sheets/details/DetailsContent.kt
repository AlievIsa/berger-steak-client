package com.alievisa.bergersteak.ui.sheets.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import berger_steak_client.composeapp.generated.resources.Res
import berger_steak_client.composeapp.generated.resources.order_details
import berger_steak_client.composeapp.generated.resources.pay
import berger_steak_client.composeapp.generated.resources.restaurant
import berger_steak_client.composeapp.generated.resources.to_take_away
import berger_steak_client.composeapp.generated.resources.to_the_table
import berger_steak_client.composeapp.generated.resources.total
import com.alievisa.bergersteak.domain.models.BasketModel
import com.alievisa.bergersteak.domain.models.RestaurantModel
import com.alievisa.bergersteak.ui.common.MainButton
import com.alievisa.bergersteak.ui.common.TwoOptionSegmentedControl
import com.alievisa.bergersteak.ui.theme.AppDefaults
import com.alievisa.bergersteak.ui.utils.ScaleIndication
import com.alievisa.bergersteak.ui.utils.extensions.rub
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DetailsContent(
    viewModel: DetailsViewModel = koinViewModel(),
    navController: NavController,
    basketModel: BasketModel,
    showInBottomSheet: Boolean = false,
    onPayButtonClick: () -> Unit,
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val mainModifier = if (showInBottomSheet) {
        Modifier.wrapContentHeight().fillMaxWidth()
    } else {
        Modifier.fillMaxSize()
    }

    Column(
        modifier = mainModifier
            .padding(horizontal = 20.dp)
            .padding(top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(Res.string.order_details),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(20.dp))

        TwoOptionSegmentedControl(
            options = listOf(stringResource(Res.string.to_take_away), stringResource(Res.string.to_the_table)),
            selectedIndex = state.selectedOrderTypeIndex,
            onOptionSelected = { viewModel.onOrderTypeSelected(it) }
        )

        Spacer(modifier = Modifier.height(20.dp))

        RestaurantDropdown(
            restaurants = state.restaurants,
            selectedIndex = state.selectedRestaurantIndex,
            onIndexSelected = { selected ->
                viewModel.onRestaurantSelected(selected)
            },
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "${stringResource(Res.string.total)} ${1290.rub()}",
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 16.sp,
            maxLines = 1,
            color = Color.Black,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(20.dp))

        MainButton(
            modifier = Modifier
                .padding(bottom = 12.dp),
            centerText = stringResource(Res.string.pay),
            isLoading = state.paymentState == PaymentState.LOADING,
            onClick = {
                viewModel.onPayButtonClick(basketModel, onPayButtonClick)
            }
        )
    }
}

@Composable
fun RestaurantDropdown(
    restaurants: List<RestaurantModel>,
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    onIndexSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val restaurantModel = restaurants.getOrNull(selectedIndex)

    Column(modifier = modifier) {
        Text(
            text = stringResource(Res.string.restaurant),
            fontSize = 16.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier.clickable(
                indication = ScaleIndication,
                interactionSource = null,
            ) {
                expanded = !expanded
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(AppDefaults.Colors.gray)
                    .padding(vertical = 12.dp, horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = restaurantModel?.address ?: "",
                    color = Color.Black,
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                restaurants.forEachIndexed { index, restaurant ->
                    DropdownMenuItem(onClick = {
                        expanded = false
                        onIndexSelected(index)
                    }) {
                        Text(text = restaurant.address)
                    }
                }
            }
        }
    }
}