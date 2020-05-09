package es.santyarbo.myfootball.data

import es.santyarbo.myfootball.data.database.countries.Country as DbCountry
import es.santyarbo.domain.Team as DomainTeam
import es.santyarbo.myfootball.data.database.teams.Team as DbTeam
import es.santyarbo.myfootball.data.server.teams.Team as ServerTeam
import es.santyarbo.domain.Fixtures as DomainFixtures
import es.santyarbo.myfootball.data.server.leagues.Fixtures as ServerFixtures
import es.santyarbo.domain.Coverage as DomainCoverage
import es.santyarbo.myfootball.data.server.leagues.Coverage as ServerCoverage
import es.santyarbo.domain.League as DomainLeague
import es.santyarbo.myfootball.data.server.leagues.League as ServerLeague
import es.santyarbo.domain.Country as DomainCountry
import es.santyarbo.myfootball.data.server.countries.Country as ServerCountry

fun ServerCountry.toDomainCountry(): DomainCountry = DomainCountry(0, country, code, flag)

fun DomainCountry.toRoomCountry(): DbCountry = DbCountry(id, country, code, flag)

fun DbCountry.toDomainCountry(): DomainCountry = DomainCountry(id, country, code, flag)

fun ServerLeague.toDomainLeague(): DomainLeague = DomainLeague(
    0,
    leagueId,
    name,
    type,
    country,
    countryCode,
    season,
    seasonStart,
    seasonEnd,
    logo,
    flag,
    standings,
    isCurrent,
    coverage.toDomainCoverage()
)

fun ServerCoverage.toDomainCoverage(): DomainCoverage = DomainCoverage(
    standings,
    fixtures.toDomainFixtures(),
    players,
    topScorers,
    predictions,
    odds
)

fun ServerFixtures.toDomainFixtures(): DomainFixtures =
    DomainFixtures(events, lineups, statistics, playersStatistics)

fun DomainTeam.toRoomTeam() = DbTeam(
    0,
    teamId,
    name,
    code,
    logo,
    country,
    isNational,
    founded,
    venueName,
    venueSurface,
    venueAddress,
    venueCity,
    venueCapacity,
    favorite,
    leagueId
)

fun DomainTeam.toRoomUpdateTeam() = DbTeam(
    id,
    teamId,
    name,
    code,
    logo,
    country,
    isNational,
    founded,
    venueName,
    venueSurface,
    venueAddress,
    venueCity,
    venueCapacity,
    favorite,
    leagueId
)

fun ServerTeam.toDomainTeam(leagueId: Int) = DomainTeam (
    0,
    teamId,
    name,
    code,
    logo,
    country,
    isNational,
    founded,
    venueName,
    venueSurface,
    venueAddress,
    venueCity,
    venueCapacity,
    false,
    leagueId
)

fun DbTeam.toDomainTeam() = DomainTeam (
    id,
    teamId,
    name,
    code,
    logo,
    country,
    isNational,
    founded,
    venueName,
    venueSurface,
    venueAddress,
    venueCity,
    venueCapacity,
    favorite,
    leagueId
)