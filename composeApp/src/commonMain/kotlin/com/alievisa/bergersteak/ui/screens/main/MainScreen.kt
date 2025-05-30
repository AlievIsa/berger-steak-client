package com.alievisa.bergersteak.ui.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import berger_steak_client.composeapp.generated.resources.Res
import berger_steak_client.composeapp.generated.resources.app_name
import berger_steak_client.composeapp.generated.resources.bag
import berger_steak_client.composeapp.generated.resources.burger_logo
import berger_steak_client.composeapp.generated.resources.further
import com.alievisa.bergersteak.Screen
import com.alievisa.bergersteak.data.CategoryModel
import com.alievisa.bergersteak.data.DishModel
import com.alievisa.bergersteak.data.MenuModel
import com.alievisa.bergersteak.data.MockUser
import com.alievisa.bergersteak.data.OrderModel
import com.alievisa.bergersteak.data.OrderStatus
import com.alievisa.bergersteak.data.PositionModel
import com.alievisa.bergersteak.data.dishesListMock
import com.alievisa.bergersteak.data.menuMock
import com.alievisa.bergersteak.ui.common.CollapsingSearchToolbar
import com.alievisa.bergersteak.ui.common.DraggableBottomSheet
import com.alievisa.bergersteak.ui.common.MainButton
import com.alievisa.bergersteak.ui.common.MenuList
import com.alievisa.bergersteak.ui.common.ActiveOrdersList
import com.alievisa.bergersteak.ui.common.BottomSheetContent
import com.alievisa.bergersteak.ui.common.DishesList
import com.alievisa.bergersteak.ui.common.ProfileButton
import com.alievisa.bergersteak.ui.common.ToolbarButton
import com.alievisa.bergersteak.ui.screens.aboutus.AboutUsScreen
import com.alievisa.bergersteak.ui.screens.auth.AuthScreen
import com.alievisa.bergersteak.ui.screens.dish.DishScreen
import com.alievisa.bergersteak.ui.screens.info.OrderInfoScreen
import com.alievisa.bergersteak.utils.extensions.rub
import com.alievisa.bergersteak.utils.shimmerBackground
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun MainScreen(navController: NavController) {

    val menuScrollState = rememberLazyListState()
    var currentBottomSheetContent by remember { mutableStateOf<BottomSheetContent?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var isMainButtonVisible by remember { mutableStateOf(true) }
    var state by remember { mutableStateOf(MainScreenState.LOADING) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(state) {
        if (state == MainScreenState.LOADING) {
            delay(3000)
            state = MainScreenState.CONTENT
        }
    }

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
                    isLoading = state == MainScreenState.LOADING,
                    onClick = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                        if (MockUser.isAuthorized) {
                            navController.navigate(Screen.Profile)
                        } else {
                            currentBottomSheetContent = BottomSheetContent.Authorization
                        }
                    }
                )
            },
            rightButton = {
                ToolbarButton(
                    icon = vectorResource(Res.drawable.burger_logo),
                    onClick = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                        currentBottomSheetContent = BottomSheetContent.AboutUs
                    }
                )
            },
            searchQuery = searchQuery,
            onSearchQueryChanged = { searchQuery = it },
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
            when (state) {
                MainScreenState.CONTENT -> {
                    if (searchQuery.isBlank()) {
                        Column {
                            if (MockUser.isAuthorized) {
                                ActiveOrdersList(
                                    orders = MockUser.data.orders,
                                    onOrderClick = { orderModel ->
                                        currentBottomSheetContent = BottomSheetContent.OrderInfo(orderModel)
                                    }
                                )
                            }
                            MenuList(
                                menuModel = menuMock,
                                menuScrollState = menuScrollState,
                                isMainButtonVisible = isMainButtonVisible,
                                onDishClick = { dishModel ->
                                    focusManager.clearFocus()
                                    keyboardController?.hide()
                                    currentBottomSheetContent = BottomSheetContent.Dish(dishModel)
                                },
                                onAddDishClick = {
                                    focusManager.clearFocus()
                                    keyboardController?.hide()
                                }
                            )
                        }
                    } else {
                        DishesList(
                            dishes = dishesListMock.filter { it.name.contains(searchQuery, ignoreCase = true) },
                            isMainButtonVisible = isMainButtonVisible,
                        )
                    }

                    if (isMainButtonVisible) {
                        MainButton(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(horizontal = 20.dp)
                                .padding(bottom = 12.dp),
                            centerText = stringResource(Res.string.further),
                            leftIcon = vectorResource(Res.drawable.bag),
                            rightText = 500.rub(),
                            onClick = {
                                focusManager.clearFocus()
                                keyboardController?.hide()
                                navController.navigate(Screen.Basket)
                            }
                        )
                    }
                }
                MainScreenState.LOADING -> {
                    MenuShimmer()
                }
                MainScreenState.ERROR -> {}
            }
        }
    }

    DraggableBottomSheet(
        isVisible = currentBottomSheetContent != null,
        onDismiss = { currentBottomSheetContent = null }
    ) {
        when (val content = currentBottomSheetContent) {
            is BottomSheetContent.Dish -> {
                DishScreen(
                    dishModel = content.dishModel,
                    showInBottomSheet = true,
                    onDoneClick = { currentBottomSheetContent = null }
                )
            }
            is BottomSheetContent.AboutUs -> {
                AboutUsScreen(
                    showInBottomSheet = true,
                )
            }
            is BottomSheetContent.Authorization -> {
                AuthScreen(
                    navController = navController,
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

private enum class MainScreenState {
    CONTENT, LOADING, ERROR,
}

@Composable
fun MenuShimmer() {
    Column(
        modifier = Modifier.fillMaxSize().padding(vertical = 12.dp, horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            repeat(4) {
                Spacer(modifier = Modifier.height(36.dp).width(80.dp).clip(RoundedCornerShape(6.dp)).shimmerBackground())
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.height(24.dp).width(140.dp).clip(RoundedCornerShape(6.dp)).shimmerBackground())

        Spacer(modifier = Modifier.height(12.dp))

        repeat(2) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Spacer(modifier = Modifier.height(280.dp).weight(1f).clip(RoundedCornerShape(12.dp)).shimmerBackground())
                Spacer(modifier = Modifier.width(16.dp))
                Spacer(modifier = Modifier.height(280.dp).weight(1f).clip(RoundedCornerShape(12.dp)).shimmerBackground())
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}