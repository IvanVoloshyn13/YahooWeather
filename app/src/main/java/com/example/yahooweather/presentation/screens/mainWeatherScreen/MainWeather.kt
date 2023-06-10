package com.example.yahooweather.presentation.screens.mainWeatherScreen

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.GpsStatus
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.yahooweather.R
import com.example.yahooweather.presentation.screens.mainWeatherScreen.components.PermissionDialog
import com.example.yahooweather.presentation.screens.mainWeatherScreen.components.TopAppBar
import com.example.yahooweather.ui.theme.LightBlue
import com.example.yahooweather.ui.theme.Typography
import com.google.accompanist.permissions.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
fun MainWeatherScreen() {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val viewModel: MainWeatherViewModel = hiltViewModel()
    val context = LocalContext.current
    val weatherState = viewModel.weatherState.collectAsState()
    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val imagePainter = rememberAsyncImagePainter(model = weatherState.value.currentCityImage.data)





    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray),
        contentAlignment = Alignment.Center
    )


    {
        Image(
            painter = imagePainter, contentDescription = "background image",
            modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop
        )

        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(modifier = Modifier, onNavigationIconClick = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }

                }, cityName = weatherState.value.currentLocationState.cityName)
            },

            drawerContent = {
                DrawerHeader(modifier = Modifier,
                    onCurrentLocationClick = {
                        viewModel.sendEvent(MainWeatherEvent.CurrentLocationEvent)
                        coroutineScope.launch {
                            scaffoldState.drawerState.close()
                        }
                    })
            },
            backgroundColor = Color.Transparent,
            drawerBackgroundColor = Color.Black,
        ) { paddingVal ->

            if (!permissionState.status.isGranted) {
                PermissionDialog(
                    onCancelClick = { /*TODO*/ },
                    onGoToAppSettingsClick = { onGoToAppSettings(context) },
                    modifier = Modifier
                )

            }
            if (!weatherState.value.gpsState) {
                Toast.makeText(context, "GPS is disabled", Toast.LENGTH_SHORT).show()
            } else {
                LaunchedEffect(Unit) {
                    viewModel.sendEvent(MainWeatherEvent.CurrentLocationEvent)
                }
            }


        }
    }

}


@Composable
fun DrawerHeader(modifier: Modifier, onCurrentLocationClick: () -> Unit) {
    Column(
        modifier = modifier.height(80.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(all = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "person icon",
                tint = Color.White
            )
            Text(
                modifier = modifier.padding(start = 8.dp),
                text = stringResource(id = R.string.login_register_text_btt),
                style = Typography.bodySmall,
                color = LightBlue
            )

        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(all = 6.dp)
                .clickable { onCurrentLocationClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Current Location",
                tint = Yellow
            )
            Text(text = "Current location", style = Typography.bodySmall, color = LightBlue)
        }


        Divider(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
            color = Color.DarkGray
        )


    }

}

fun onGoToAppSettings(context: Context) {
    val intent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", context.packageName, null)
    )
    context.startActivity(intent)
}


@Preview
@Composable
fun StartScreenPrev() {
    MainWeatherScreen()
}