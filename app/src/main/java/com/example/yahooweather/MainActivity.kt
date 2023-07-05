package com.example.yahooweather

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.data.utils.GpsStatusReceiver
import com.example.yahooweather.navigation.SetupNavHost
import com.example.yahooweather.presentation.screens.mainWeatherScreen.MainWeatherViewModel
import com.example.yahooweather.ui.theme.YahooWeatherTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModel: MainActivityViewModel

    @Inject
    lateinit var gpsStatusReceiver: GpsStatusReceiver

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

    override fun onResume() {
        super.onResume()
        IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION).also {
            registerReceiver(gpsStatusReceiver, it)
        }
        val gpsStatus = intent.getBooleanExtra("gps", false)
        Log.d("GPS", gpsStatus.toString())

    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(gpsStatusReceiver)
    }


}



