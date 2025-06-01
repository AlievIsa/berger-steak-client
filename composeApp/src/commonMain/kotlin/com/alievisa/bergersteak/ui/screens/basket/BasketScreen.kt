package com.alievisa.bergersteak.ui.screens.basket

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import berger_steak_client.composeapp.generated.resources.Res
import berger_steak_client.composeapp.generated.resources.arrow_back
import berger_steak_client.composeapp.generated.resources.bag
import berger_steak_client.composeapp.generated.resources.basket
import berger_steak_client.composeapp.generated.resources.delete_all
import berger_steak_client.composeapp.generated.resources.good_addition_to_order
import berger_steak_client.composeapp.generated.resources.options
import berger_steak_client.composeapp.generated.resources.to_order
import com.alievisa.bergersteak.domain.models.DishModel
import com.alievisa.bergersteak.domain.models.MockUser
import com.alievisa.bergersteak.ui.Screen
import com.alievisa.bergersteak.ui.common.BasketItem
import com.alievisa.bergersteak.ui.common.BottomSheetContent
import com.alievisa.bergersteak.ui.common.DraggableBottomSheet
import com.alievisa.bergersteak.ui.common.MainButton
import com.alievisa.bergersteak.ui.common.MenuItem
import com.alievisa.bergersteak.ui.common.Toolbar
import com.alievisa.bergersteak.ui.common.ToolbarButton
import com.alievisa.bergersteak.ui.sheets.auth.AuthContent
import com.alievisa.bergersteak.ui.sheets.details.DetailsContent
import com.alievisa.bergersteak.ui.sheets.dish.DishContent
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
        onDishClick = { dishModel ->
            currentBottomSheetContent = BottomSheetContent.Dish(dishModel)
        },
        onIncreaseAmountClick = { dishModel ->
            viewModel.onIncreaseDishAmountClick(dishModel)
        },
        onDecreaseAmountClick = { dishModel ->
            viewModel.onDecreaseDishAmountClick(dishModel)
        },
        onMainButtonClick = {
            if (MockUser.isAuthorized) {
                currentBottomSheetContent = BottomSheetContent.OrderDetails
            } else {
                currentBottomSheetContent = BottomSheetContent.Authorization
            }
        },
        onDeleteAllClick = {
            viewModel.clearBasket()
        },
        onAddDishClick = { dishModel ->
            viewModel.onAddDishClick(dishModel)
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
                    initialAmount = state.basketModel.positions
                        .firstOrNull { it.dishModel.id == content.dishModel.id }?.dishAmount ?: 0,
                    showInBottomSheet = true,
                    onDoneClick = { dishAmount ->
                        viewModel.onDoneDishClick(content.dishModel, dishAmount)
                        currentBottomSheetContent = null
                    }
                )
            }
            is BottomSheetContent.OrderDetails -> {
                DetailsContent(
                    navController = navController,
                    basketModel = state.basketModel,
                    showInBottomSheet = true,
                    onPayButtonClick = {
                        navController.navigate(Screen.Main) {
                            popUpTo(Screen.Main) { inclusive = true }
                        }
                    }
                )
            }
            is BottomSheetContent.Authorization -> {
                AuthContent(
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

    var didSkipFirstEmpty by remember { mutableStateOf(false) }

    LaunchedEffect(state) {
        if (didSkipFirstEmpty.not() && state.basketModel.positions.isEmpty()) {
            didSkipFirstEmpty = true
            return@LaunchedEffect
        }

        if (state.basketModel.positions.isEmpty()) {
            navController.popBackStack()
        }
    }
}

@Composable
fun BasketScreenContent(
    state: BasketState,
    onBackButtonClick: () -> Unit,
    onIncreaseAmountClick: (DishModel) -> Unit,
    onDecreaseAmountClick: (DishModel) -> Unit,
    onDishClick: (DishModel) -> Unit,
    onMainButtonClick: () -> Unit,
    onDeleteAllClick: () -> Unit,
    onAddDishClick: (DishModel) -> Unit,
) {
    var isRightButtonExpanded by remember { mutableStateOf(false) }

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
                Box {
                    ToolbarButton(
                        icon = vectorResource(Res.drawable.options),
                        onClick = { isRightButtonExpanded = true }
                    )
                    DropdownMenu(
                        expanded = isRightButtonExpanded,
                        onDismissRequest = { isRightButtonExpanded = false }
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                onDeleteAllClick()
                                isRightButtonExpanded = false
                            }
                        ) {
                            Text(stringResource(Res.string.delete_all))
                        }
                    }
                }
            },
        )

        Box(modifier = Modifier.fillMaxSize()) {

            LazyColumn(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp)
            ) {
                item { Spacer(modifier = Modifier.height(12.dp)) }

                items(state.basketModel.positions) { positionModel ->
                    BasketItem(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        positionModel = positionModel,
                        onIncreaseAmountClick = { onIncreaseAmountClick(positionModel.dishModel) },
                        onDecreaseAmountClick = { onDecreaseAmountClick(positionModel.dishModel) },
                        onDishClick = { onDishClick(positionModel.dishModel) }
                    )
                }

                item {
                    Text(
                        text = stringResource(Res.string.good_addition_to_order),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .padding(top = 8.dp, bottom = 4.dp),
                    )
                }

                when (state.recommendationsState) {
                    is RecommendationsState.Content -> {

                        items(state.recommendationsState.recommendations.chunked(2)) { row ->
                            Row {
                                row.forEach { dishModel ->
                                    MenuItem(
                                        dishModel = dishModel,
                                        onDishClick = { onDishClick(dishModel) },
                                        onAddDishClick = { onAddDishClick(dishModel) },
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                                if (row.size == 1) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                    RecommendationsState.Loading -> item { RecommendationsShimmer() }
                    RecommendationsState.Error -> {}
                }

                item {
                    Spacer(modifier = Modifier.height(70.dp))
                }
            }

            MainButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 12.dp),
                centerText = stringResource(Res.string.to_order),
                leftIcon = vectorResource(Res.drawable.bag),
                rightText = state.basketModel.totalPrice.rub(),
                onClick = { onMainButtonClick() }
            )
        }
    }
}