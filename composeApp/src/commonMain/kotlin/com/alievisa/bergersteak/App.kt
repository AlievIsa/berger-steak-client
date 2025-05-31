package com.alievisa.bergersteak

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alievisa.bergersteak.ui.Screen
import com.alievisa.bergersteak.ui.screens.basket.BasketScreen
import com.alievisa.bergersteak.ui.screens.main.MainScreen
import com.alievisa.bergersteak.ui.screens.main.MainViewModel
import com.alievisa.bergersteak.ui.screens.profile.ProfileScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        Box(modifier = Modifier.fillMaxSize().padding(bottom = getInsetBottom())) {
            NavHost(
                navController = navController,
                startDestination = Screen.Main,
            ) {
                composable<Screen.Main> {
                    MainScreen(
                        viewModel = koinViewModel<MainViewModel>(),
                        navController = navController,
                    )
                }
                composable<Screen.Basket> {
                    BasketScreen(navController = navController)
                }
                composable<Screen.Profile> {
                    ProfileScreen(navController = navController)
                }
            }
        }
    }
}