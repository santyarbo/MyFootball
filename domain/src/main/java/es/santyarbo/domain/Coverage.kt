package es.santyarbo.domain

data class Coverage(
    val standings: Boolean,
    val fixtures: Fixtures,
    val players: Boolean,
    val topScorers: Boolean,
    val predictions: Boolean,
    val odds: Boolean
)