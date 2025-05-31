package com.alievisa.bergersteak.ui.screens.basket

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import berger_steak_client.composeapp.generated.resources.Res
import berger_steak_client.composeapp.generated.resources.arrow_back
import berger_steak_client.composeapp.generated.resources.bag
import berger_steak_client.composeapp.generated.resources.basket
import berger_steak_client.composeapp.generated.resources.options
import berger_steak_client.composeapp.generated.resources.to_order
import com.alievisa.bergersteak.domain.models.MockUser
import com.alievisa.bergersteak.domain.models.PositionModel
import com.alievisa.bergersteak.ui.Screen
import com.alievisa.bergersteak.ui.common.BasketItem
import com.alievisa.bergersteak.ui.common.BottomSheetContent
import com.alievisa.bergersteak.ui.common.DraggableBottomSheet
import com.alievisa.bergersteak.ui.common.MainButton
import com.alievisa.bergersteak.ui.common.Toolbar
import com.alievisa.bergersteak.ui.common.ToolbarButton
import com.alievisa.bergersteak.ui.sheets.AuthContent
import com.alievisa.bergersteak.ui.sheets.DetailsContent
import com.alievisa.bergersteak.ui.sheets.DishContent
import com.alievisa.bergersteak.ui.utils.extensions.rub
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BasketScreen(
    viewModel: BasketViewModel = koinViewModel(),
    navController: NavController,
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    var currentBottomSheetContent by remember { mutableStateOf<BottomSheetContent?>(null) }

    BasketScreenContent(
        state = state,
        onBackButtonClick = {
            navController.popBackStack()
        },
        onDishClick = { positionModel ->
            currentBottomSheetContent = BottomSheetContent.Dish(positionModel.dishModel)
        },
        onMainButtonClick = {
            if (MockUser.isAuthorized) {
                currentBottomSheetContent = BottomSheetContent.OrderDetails
            } else {
                currentBottomSheetContent = BottomSheetContent.Authorization
            }
        },
    )

    DraggableBottomSheet(
        isVisible = currentBottomSheetContent != null,
        onDismiss = { currentBottomSheetContent = null }
    ) {
        when (val content = currentBottomSheetContent) {
            is BottomSheetContent.Dish -> {
                DishContent(
                    dishModel = content.dishModel,
                    showInBottomSheet = true,
                    onDoneClick = { currentBottomSheetContent = null }
                )
            }
            is BottomSheetContent.OrderDetails -> {
                DetailsContent(
                    navController = navController,
                    basketModel = state.basketModel,
                    showInBottomSheet = true,
                )
            }
            is BottomSheetContent.Authorization -> {
                AuthContent(
                    //navController = navController,
                    showInBottomSheet = true,
                    onSuccess = {
                        if (MockUser.data.name.isEmpty()) {
                            navController.navigate(Screen.Profile)
                        } else {
                            currentBottomSheetContent = BottomSheetContent.OrderDetails
                        }
                        MockUser.isAuthorized = true
                    }
                )
            }
            else -> {}
        }
    }
}

@Composable
fun BasketScreenContent(
    state: BasketState,
    onBackButtonClick: () -> Unit,
    onDishClick: (PositionModel) -> Unit,
    onMainButtonClick: () -> Unit,
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Toolbar(
            title = stringResource(Res.string.basket),
            leftPart = {
                ToolbarButton(
                    icon = vectorResource(Res.drawable.arrow_back),
                    onClick = {
                        onBackButtonClick()
                    }
                )
            },
            rightPart = {
                ToolbarButton(
                    icon = vectorResource(Res.drawable.options),
                )
            },
        )

        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().padding(20.dp)
            ) {
                items(state.basketModel.positions) { positionModel ->
                    BasketItem(
                        positionModel = positionModel,
                        onDishClick = { onDishClick(positionModel) }
                    )
                }
            }

            MainButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 12.dp),
                centerText = stringResource(Res.string.to_order),
                leftIcon = vectorResource(Res.drawable.bag),
                rightText = 500.rub(),
                onClick = { onMainButtonClick() }
            )
        }
    }
}