package com.example.yahooweather.presentation.screens.mainWeatherScreen

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission())
        { wasGranted ->
            if (wasGranted) {
                Toast.makeText(context, "Permission was Granted", Toast.LENGTH_SHORT).show()
            }
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray),
        contentAlignment = Alignment.Center
    )
    {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(modifier = Modifier, onNavigationIconClick = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }
                })
            },

            drawerContent = {
                DrawerHeader(modifier = Modifier)
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

            } else {

            }

        }
    }

}


@Composable
fun DrawerHeader(modifier: Modifier) {
    Column(
        modifier = modifier.height(60.dp),
        verticalArrangement = Arrangement.Center
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
        Divider(modifier = modifier.padding(top = 6.dp))
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