package es.santyarbo.myfootball.data.server.countries

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class ApiCountry(
    val api: CountryResult
)

data class CountryResult(
    val results: Int,
    val countries: List<Country>
)

@Parcelize
data class Country(
    val country: String,
    val code: String?,
    val flag: String?
) : Parcelable