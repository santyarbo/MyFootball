package es.santyarbo.domain

data class Team (
    val id: Int,
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