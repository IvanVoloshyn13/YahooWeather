package com.example.data.repository

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.data.mappers.toDomainLocation
import com.example.domain.Resource
import com.example.domain.location.CurrentLocation
import com.example.domain.location.LocationTracker
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject


@ExperimentalCoroutinesApi
class LocationTrackerImpl @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    @ApplicationContext private val context: Context
) : LocationTracker {


    override suspend fun getUserLocation(): Resource<CurrentLocation> {
        val geocoder = Geocoder(context)

        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val isGpsEnabled =
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                    || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
            )

        if (!hasAccessFineLocationPermission || !hasAccessCoarseLocationPermission || !isGpsEnabled) {
            return Resource.Error(message = "GPS is disabled")
        }

        @Suppress("DEPRECATION")
        return suspendCancellableCoroutine { continuation ->
            locationClient.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful) {
                        val city =
                            geocoder.getFromLocation(result.latitude, result.longitude, 1)
                        continuation.resume(Resource.Success(data = result.toDomainLocation(cityName = city.toString()))) {
                        }
                    } else {
                        continuation.resume(Resource.Error(message = "")) {
                        }

                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener { it ->
                    val city =
                        geocoder.getFromLocation(it.latitude, it.longitude, 1)?.get(0)?.locality
                    continuation.resume(Resource.Success(data = it.toDomainLocation(cityName = city.toString()))) {

                    }
                }

                addOnFailureListener { it ->
                    continuation.resume(Resource.Error(data = null, message = it.message)) {
                        continuation.cancel()
                    }
                }

                addOnCanceledListener {
                    continuation.cancel()
                }
            }
        }

    }


}