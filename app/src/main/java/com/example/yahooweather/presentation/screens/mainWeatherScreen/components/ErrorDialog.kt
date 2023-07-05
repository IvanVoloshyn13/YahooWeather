package com.example.yahooweather.presentation.screens.mainWeatherScreen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.yahooweather.ui.theme.LightBlue

@Composable
fun ErrorDialog(modifier: Modifier, onCancelClick: () -> Unit) {
    Card(
        modifier = modifier
            .padding(start = 12.dp, end = 12.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = RectangleShape,
        border = BorderStroke(width = 2.dp, color = Color.DarkGray)
    ) {
        Column(
            modifier = modifier.padding(12.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Gps is disabled", color = Color.DarkGray)
            Text(text = "Cancel", color = Color.DarkGray)

        }

    }
}