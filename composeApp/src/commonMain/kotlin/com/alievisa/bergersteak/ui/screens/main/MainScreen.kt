package com.alievisa.bergersteak.ui.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import berger_steak_client.composeapp.generated.resources.Res
import berger_steak_client.composeapp.generated.resources.app_name
import berger_steak_client.composeapp.generated.resources.bag
import berger_steak_client.composeapp.generated.resources.burger_logo
import berger_steak_client.composeapp.generated.resources.further
import com.alievisa.bergersteak.domain.models.DishModel
import com.alievisa.bergersteak.domain.models.MockUser
import com.alievisa.bergersteak.domain.models.OrderModel
import com.alievisa.bergersteak.domain.models.toDishesList
import com.alievisa.bergersteak.ui.Screen
import com.alievisa.bergersteak.ui.common.ActiveOrdersList
import com.alievisa.bergersteak.ui.common.BottomSheetContent
import com.alievisa.bergersteak.ui.common.CollapsingSearchToolbar
import com.alievisa.bergersteak.ui.common.DishesList
import com.alievisa.bergersteak.ui.common.DraggableBottomSheet
import com.alievisa.bergersteak.ui.common.MainButton
import com.alievisa.bergersteak.ui.common.MenuList
import com.alievisa.bergersteak.ui.common.ProfileButton
import com.alievisa.bergersteak.ui.common.ToolbarButton
import com.alievisa.bergersteak.ui.sheets.AboutUsContent
import com.alievisa.bergersteak.ui.sheets.AuthContent
import com.alievisa.bergersteak.ui.sheets.DishContent
import com.alievisa.bergersteak.ui.sheets.OrderInfoScreen
import com.alievisa.bergersteak.ui.utils.extensions.rub
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel = koinViewModel(),
    navController: NavController,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var currentBottomSheetContent by remember { mutableStateOf<BottomSheetContent?>(null) }

    MainScreenContent(
        state = state,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onProfileButtonClick = {
            if (MockUser.isAuthorized) {
                navController.navigate(Screen.Profile)
            } else {
                currentBottomSheetContent = BottomSheetContent.Authorization
            }
        },
        onBurgerButtonClick = {
            currentBottomSheetContent = BottomSheetContent.AboutUs
        },
        onMainButtonClick = {
            navController.navigate(Screen.Basket)
        },
        onOrderClick = { orderModel ->
            currentBottomSheetContent = BottomSheetContent.OrderInfo(orderModel)
        },
        onDishClick = { dishModel ->
            currentBottomSheetContent = BottomSheetContent.Dish(dishModel)
        },
        onAddDishClick = { dishModel ->
            viewModel
        }
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
            is BottomSheetContent.AboutUs -> {
                AboutUsContent(
                    showInBottomSheet = true,
                )
            }
            is BottomSheetContent.Authorization -> {
                AuthContent(
                    showInBottomSheet = true,
                    onSuccess = {
                        if (MockUser.data.name.isEmpty()) {
                            navController.navigate(Screen.Profile)
                        } else {
                            currentBottomSheetContent = null
                        }
                        MockUser.isAuthorized = true

                    }
                )
            }
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

@Composable
fun MainScreenContent(
    state: MainState,
    onSearchQueryChanged: (String) -> Unit,
    onProfileButtonClick: () -> Unit,
    onBurgerButtonClick: () -> Unit,
    onMainButtonClick: () -> Unit,
    onOrderClick: (OrderModel) -> Unit,
    onDishClick: (DishModel) -> Unit,
    onAddDishClick: (DishModel) -> Unit,
) {

    val menuScrollState = rememberLazyListState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable(indication = null, interactionSource = null) { focusManager.clearFocus() },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CollapsingSearchToolbar(
            title = stringResource(Res.string.app_name),
            listState = menuScrollState,
            leftButton = {
                ProfileButton(
                    userName = if (MockUser.isAuthorized) "Иса Алиев" else "?",
                    isLoading = state.userState == UserState.Loading,
                    onClick = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                        onProfileButtonClick()
                    }
                )
            },
            rightButton = {
                ToolbarButton(
                    icon = vectorResource(Res.drawable.burger_logo),
                    onClick = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                        onBurgerButtonClick()
                    }
                )
            },
            isSearchEnabled = state.menuState is MenuState.Content,
            searchQuery = state.searchQuery,
            onSearchQueryChanged = { onSearchQueryChanged(it) },
            onCollapsedSearchIconClick = {
                coroutineScope.launch {
                    menuScrollState.animateScrollToItem(index = 0)
                    delay(200)
                    focusRequester.requestFocus()
                    keyboardController?.show()
                }
            },
            focusManager = focusManager,
            focusRequester = focusRequester,
            keyboardController = keyboardController,
        )
        Box(modifier = Modifier.fillMaxSize()) {
            when (state.menuState) {
                is MenuState.Content -> {
                    if (state.searchQuery.isBlank()) {
                        Column {
                            if (MockUser.isAuthorized) {
                                ActiveOrdersList(
                                    orders = MockUser.data.orders,
                                    onOrderClick = { orderModel ->
                                        focusManager.clearFocus()
                                        keyboardController?.hide()
                                        onOrderClick(orderModel)
                                    }
                                )
                            }
                            MenuList(
                                menuModel = state.menuState.menuModel,
                                menuScrollState = menuScrollState,
                                isMainButtonVisible = state.mainButtonState.isVisible,
                                onDishClick = { dishModel ->
                                    focusManager.clearFocus()
                                    keyboardController?.hide()
                                    onDishClick(dishModel)
                                },
                                onAddDishClick = { dishModel ->
                                    focusManager.clearFocus()
                                    keyboardController?.hide()
                                    onAddDishClick(dishModel)
                                }
                            )
                        }
                    } else {
                        DishesList(
                            dishes = state.menuState.menuModel.toDishesList()
                                .filter { it.name.contains(state.searchQuery, ignoreCase = true) },
                            isMainButtonVisible = state.mainButtonState.isVisible
                        )
                    }

                    if (state.mainButtonState.isVisible) {
                        MainButton(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(horizontal = 20.dp)
                                .padding(bottom = 12.dp),
                            centerText = stringResource(Res.string.further),
                            leftIcon = vectorResource(Res.drawable.bag),
                            rightText = state.mainButtonState.amount.rub(),
                            onClick = {
                                focusManager.clearFocus()
                                keyboardController?.hide()
                                onMainButtonClick()
                            }
                        )
                    }
                }
                MenuState.Loading -> {
                    MenuShimmer()
                }
                MenuState.Error -> {
                    Text(text = "Ошибка")
                }
            }
        }
    }
}