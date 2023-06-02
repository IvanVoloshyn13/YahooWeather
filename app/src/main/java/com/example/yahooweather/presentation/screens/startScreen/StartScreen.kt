package com.example.yahooweather.presentation.screens.startScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.yahooweather.presentation.screens.startScreen.components.TopAppBar
import kotlinx.coroutines.launch

@Composable
fun StartScreen() {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),

        ) {
        Scaffold(
            modifier = Modifier.fillMaxSize().background(Color.Transparent),
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(modifier = Modifier, onNavigationIconClick = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }
                })
            },
            backgroundColor = Color.Transparent
        ) { paddingValues ->
            paddingValues

        }
    }
}


@Preview
@Composable
fun StartScreenPrev() {
    StartScreen()
}