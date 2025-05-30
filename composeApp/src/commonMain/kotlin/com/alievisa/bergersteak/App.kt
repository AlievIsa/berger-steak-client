package com.alievisa.bergersteak

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alievisa.bergersteak.ui.screens.aboutus.AboutUsScreen
import com.alievisa.bergersteak.ui.screens.auth.AuthScreen
import com.alievisa.bergersteak.ui.screens.basket.BasketScreen
import com.alievisa.bergersteak.ui.screens.details.DetailsScreen
import com.alievisa.bergersteak.ui.screens.dish.DishScreen

import com.alievisa.bergersteak.ui.screens.main.MainScreen
import com.alievisa.bergersteak.ui.screens.profile.ProfileScreen
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

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
                    MainScreen(navController)
                }
                composable<Screen.Basket> {
                    BasketScreen(navController)
                }
                composable<Screen.Profile> {
                    ProfileScreen(navController)
                }
            }
        }
    }
}