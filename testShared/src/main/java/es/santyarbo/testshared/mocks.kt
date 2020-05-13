package es.santyarbo.testshared

import es.santyarbo.domain.*

val mockedTeam = Team(
    0,
    0,
    "Real Madrid",
    "rm",
    "",
    "España",
    false,
    1902,
    "Santiago Bernabeu",
    "grass",
    "Paseo La Castellana",
    "Madrid",
    80000,
    false,
    0
)

val mockedCountry = Country(0, "España", "es", null)

val mockedLeague = League(
    0,
    0,
    "Primera División",
    "",
    "España",
    "es",
    2020,
    "2019",
    "2020",
    null,
    "",
    0,
    0,
    Coverage(
        standings = true,
        fixtures = Fixtures(events = true, lineups = true, statistics = true, playersStatistics = true),
        players = true,
        topScorers = true,
        predictions = true,
        odds = true
    )
)