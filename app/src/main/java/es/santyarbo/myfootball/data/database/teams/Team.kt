package es.santyarbo.myfootball.data.database.teams

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Team (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val teamId: Int,
    val name: String,
    val code: String?,
    val logo: String?,
    val country: String,
    val isNational: Boolean,
    val founded: Int,
    val venueName: String?,
    val venueSurface: String?,
    val venueAddress: String?,
    val venueCity: String?,
    val venueCapacity: Int,
    val favorite: Boolean,
    val leagueId: Int
)