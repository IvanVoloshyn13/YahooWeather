package com.example.yahooweather.navigation

import WelcomeScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.yahooweather.presentation.screens.mainWeatherScreen.MainWeatherScreen
import com.example.yahooweather.utils.Constants

@Composable
fun SetupNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.Welcome.route) {
        composable(route = Screens.Welcome.route) {
            WelcomeScreen(navController)
        }
        composable(route = Screens.AddNewCity.route) {

        }
        composable(route = Screens.MainWeather.route) {
            MainWeatherScreen()
        }
    }

}

sealed class Screens(val route: String) {
    object MainWeather : Screens(route = Constants.Screens.MAIN_WEATHER_SCREEN)
    object AddNewCity : Screens(route = Constants.Screens.ADD_NEW_CITY_SCREEN)
    object Welcome : Screens(route = Constants.Screens.WELCOME_SCREEN)

}

