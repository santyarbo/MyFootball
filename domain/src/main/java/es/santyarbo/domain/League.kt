package es.santyarbo.domain

data class League(
    val id: Int,
    val leagueId: Int,
    val name: String,
    val type: String,
    val country: String,
    val countryCode: String,
    val season: Int,
    val seasonStart: String,
    val seasonEnd: String,
    val logo: String?,
    val flag: String,
    val standings: Int,
    val isCurrent: Int,
    val coverage: Coverage
)