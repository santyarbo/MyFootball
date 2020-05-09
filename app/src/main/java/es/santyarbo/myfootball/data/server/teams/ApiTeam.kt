package es.santyarbo.myfootball.data.server.teams

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ApiTeam(
    val api: TeamResult
)

data class TeamResult(
    val results: Int,
    val teams: List<Team>
)

@Parcelize
data class Team(
    @SerializedName("team_id") val teamId: Int,
    val name: String,
    val code: String,
    val logo: String?,
    val country: String,
    @SerializedName("is_national") val isNational: Boolean,
    val founded: Int,
    @SerializedName("venue_name") val venueName: String?,
    @SerializedName("venue_surface") val venueSurface: String?,
    @SerializedName("venue_address") val venueAddress: String?,
    @SerializedName("venue_city") val venueCity: String?,
    @SerializedName("venue_capacity") val venueCapacity: Int
) : Parcelable
