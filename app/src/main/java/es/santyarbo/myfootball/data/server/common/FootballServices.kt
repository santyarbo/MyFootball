package es.santyarbo.myfootball.data.server.common

import es.santyarbo.myfootball.data.server.countries.ApiCountry
import es.santyarbo.myfootball.data.server.leagues.ApiLeague
import es.santyarbo.myfootball.data.server.teams.ApiTeam
import retrofit2.http.GET
import retrofit2.http.Path

interface FootballServices {

    @GET("countries")
    suspend fun getCountries(): ApiCountry

    @GET("leagues/country/{countryCode}/2019")
    suspend fun getLeagues(
        @Path("countryCode") countryCode: String
    ): ApiLeague

    @GET("teams/league/{leagueCode}")
    suspend fun getTeams(
        @Path("leagueCode") leagueCode: String
    ): ApiTeam
}