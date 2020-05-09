package es.santyarbo.myfootball.data

import android.app.Application
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.LocationServices
import es.santyarbo.data.source.LocationDatasource
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume


class PlayServicesLocationDataSource(application: Application) : LocationDatasource {
    private val geocoder = Geocoder(application)
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)

    override suspend fun findLastRegion(): String? =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation
                .addOnCompleteListener {
                    continuation.resume(it.result.toRegion())
                }
        }

    private fun Location?.toRegion(): String? {
        val addresses = this?.let {
            geocoder.getFromLocation(latitude, longitude, 1)
        }
        return addresses?.firstOrNull()?.countryCode
    }
}