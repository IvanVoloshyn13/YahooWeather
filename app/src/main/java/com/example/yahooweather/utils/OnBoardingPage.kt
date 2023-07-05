package com.example.yahooweather.utils

import androidx.annotation.DrawableRes
import androidx.compose.ui.res.painterResource
import com.example.yahooweather.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String
) {
    class FirstPage : OnBoardingPage(
        image = R.drawable.cloud_blue_sky_,
        title = "Welcome"
    )

    class SecondPage : OnBoardingPage(
        image = R.drawable.cloud_blue_sky_,
        title = "Yahoo"
    )

}