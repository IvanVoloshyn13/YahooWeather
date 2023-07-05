package com.example.data.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager

import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class GpsStatusReceiver @Inject constructor(
    @ApplicationContext private val context: Context,
    private val locationManager: LocationManager,

    ) : BroadcastReceiver() {


    override fun onReceive(receiveContext: Context, intent: Intent) {
        var gpsStatusChanged = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val newIntent = Intent(LocationManager.GPS_PROVIDER)
        newIntent.putExtra("gps", gpsStatusChanged)
        LocalBroadcastManager.getInstance(context).sendBroadcast(newIntent)


    }


}