package com.example.yahooweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.yahooweather.navigation.SetupNavHost
import com.example.yahooweather.ui.theme.YahooWeatherTheme
import com.example.yahooweather.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModel: MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen().setKeepOnScreenCondition {
            !viewModel.isLoading.value
        }
        super.onCreate(savedInstanceState)
        viewModel.getOnBoardState()
        lifecycleScope.launch {
            delay(200)
            setContent {
                val onBoardState = viewModel.onBoardState.collectAsState()
                val screenRoute = onBoardState.value
                val navController = rememberNavController()
                YahooWeatherTheme {
                    // A surface container using the 'background' color from the theme
                    SetupNavHost(navController, screenRoute)


                }
            }
        }
    }

}



