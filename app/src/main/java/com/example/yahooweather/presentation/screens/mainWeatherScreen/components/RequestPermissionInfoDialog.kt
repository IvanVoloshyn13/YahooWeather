package com.example.yahooweather.presentation.screens.mainWeatherScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.yahooweather.ui.theme.Typography
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionDialog(
    onCancelClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
    modifier: Modifier,
) {

    AlertDialog(onDismissRequest = { /*TODO*/ },
        modifier = modifier.size(300.dp),
        backgroundColor = Color.Gray,
        text = {
            Text(
                text = "You disable localization permission. To enable it go to app settings" +
                        "and enable it manually "
            )
        },
        buttons = {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Cancel",
                    style = Typography.bodySmall,
                    modifier = modifier.clickable { onCancelClick() })
                Text(
                    text = "AppSettings",
                    style = Typography.bodySmall,
                    modifier = modifier.clickable { onGoToAppSettingsClick() })

            }
        })



}