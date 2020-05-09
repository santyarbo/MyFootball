package es.santyarbo.myfootball.data.server.leagues

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ApiLeague(
    val api: LeagueResult
)

data class LeagueResult(
    val results: Int,
    val leagues: List<League>
)

@Parcelize
data class League(
    @SerializedName("league_id") val leagueId: Int,
    val name: String,
    val type: String,
    val country: String,
    @SerializedName("country_code") val countryCode: String,
    val season: Int,
    @SerializedName("season_start") val seasonStart: String,
    @SerializedName("season_end") val seasonEnd: String,
    val logo: String?,
    val flag: String,
    val standings: Int,
    @SerializedName("is_current") val isCurrent: Int,
    val coverage: Coverage
) : Parcelable


@Parcelize
data class Coverage(
    val standings: Boolean,
    val fixtures: Fixtures,
    val players: Boolean,
    val topScorers: Boolean,
    val predictions: Boolean,
    val odds: Boolean

) : Parcelable

@Parcelize
data class Fixtures(
    val events: Boolean,
    val lineups: Boolean,
    val statistics: Boolean,
    @SerializedName("players_statistics")val playersStatistics: Boolean

) : Parcelable