package com.example.yahooweather.presentation.screens.mainWeatherScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.yahooweather.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(modifier: Modifier, onNavigationIconClick: () -> Unit, cityName: String) {
    CenterAlignedTopAppBar(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent),

        title = {
            Text(
                text = if (cityName != null) cityName else "CityName",
                style = Typography.bodyLarge
            )
        },
        navigationIcon = {
            IconButton(onClick = { onNavigationIconClick() }) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = "Menu Icon",
                    tint = Color.White,
                )
            }

        },
        actions = {
            Icon(Icons.Default.Search, contentDescription = "Search icon", tint = Color.White)
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
    )
}


@Preview
@Composable
fun TopAppBarPrev() {
    TopAppBar(modifier = Modifier, onNavigationIconClick = TODO(), "")
}

