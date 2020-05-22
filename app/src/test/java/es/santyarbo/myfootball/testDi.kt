package es.santyarbo.myfootball

import es.santyarbo.data.repository.PermissionChecker
import es.santyarbo.data.source.*
import es.santyarbo.domain.Country
import es.santyarbo.domain.League
import es.santyarbo.domain.ResultWrapper
import es.santyarbo.domain.Team
import es.santyarbo.testshared.mockedCountry
import es.santyarbo.testshared.mockedLeague
import es.santyarbo.testshared.mockedTeam
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

fun initMockedDi(vararg modules: Module) {
    startKoin {
        modules(listOf(mockedAppModule, dataModule) + modules)
    }
}

private val mockedAppModule = module {
    single<CountryLocalDatasource> { FakeCountryLocalDataSource() }
    single<CountryRemoteDatasource> { FakeCountryApiDataSource() }
    single<LeagueDatasource> { FakeLeagueDataSource() }
    single<LocationDatasource> { FakeLocationDataSource() }
    single<TeamLocalDatasource> { FakeTeamsLocalDataSource() }
    single<TeamRemoteDatasource> { FakeTeamsRemoteDataSource() }
    single<PermissionChecker> { FakePermissionChecker() }
    single { Dispatchers.Unconfined }
}


val defaultFakeCountries = listOf(
    mockedCountry.copy(1),
    mockedCountry.copy(2),
    mockedCountry.copy(3),
    mockedCountry.copy(4)
)

val defaultFakeLeagues = listOf(
    mockedLeague.copy(1),
    mockedLeague.copy(2),
    mockedLeague.copy(3),
    mockedLeague.copy(4)
)

val defaultFakeTeams = listOf(
    mockedTeam.copy(1),
    mockedTeam.copy(2),
    mockedTeam.copy(3),
    mockedTeam.copy(4)
)

class FakeCountryLocalDataSource : CountryLocalDatasource {

    var countries: List<Country> = emptyList()

    override suspend fun isEmpty(): Boolean = countries.isEmpty()

    override suspend fun saveCountries(countries: List<Country>) {
        this.countries = countries
    }

    override suspend fun getCountries(): List<Country> = countries

    override suspend fun findById(id: Int): Country = countries.first{ it.id == id }

    override suspend fun update(country: Country) {
        countries = countries.filterNot { it.id == country.id } + country
    }

    override suspend fun findByCode(code: String): Country = countries.first{ it.code == code }

}

class FakeCountryApiDataSource : CountryRemoteDatasource {

    var countries = defaultFakeCountries

    override suspend fun getCountries(): ResultWrapper<List<Country>>
            = ResultWrapper.Success(countries)

}

class FakeLeagueDataSource: LeagueDatasource {

    var leagues = defaultFakeLeagues

    override suspend fun findLeagues(country: Country): ResultWrapper<List<League>>
            = ResultWrapper.Success(leagues)
}

class FakeLocationDataSource : LocationDatasource {
    var location = "es"

    override suspend fun findLastRegion(): String? = location
}

class FakeTeamsLocalDataSource : TeamLocalDatasource {

    var teams: List<Team> = emptyList()

    override suspend fun isEmpty(leagueId: Int): Boolean = teams.isEmpty()

    override suspend fun saveTeams(teams: List<Team>, leagueId: Int) {
        this.teams = teams
    }

    override suspend fun getTeamsByLeague(leagueId: Int): List<Team> = teams

    override suspend fun getTeamsFavorites(): List<Team> = teams

    override suspend fun findById(id: Int): Team = teams.first { it.id == id }

    override suspend fun update(team: Team) {
        teams = teams.filterNot { it.id == team.id } + team
    }

}

class FakeTeamsRemoteDataSource : TeamRemoteDatasource {

    var teams = defaultFakeTeams

    override suspend fun getTeamsByLeague(leagueId: Int): ResultWrapper<List<Team>>
            = ResultWrapper.Success(teams)

}

class FakePermissionChecker : PermissionChecker {
    var permissionGranted = true

    override suspend fun check(permission: PermissionChecker.Permission): Boolean =
        permissionGranted
}