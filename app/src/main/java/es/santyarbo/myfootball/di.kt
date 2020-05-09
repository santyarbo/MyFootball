package es.santyarbo.myfootball

import android.app.Application
import es.santyarbo.data.repository.*
import es.santyarbo.data.source.*
import es.santyarbo.myfootball.data.AndroidPermissionChecker
import es.santyarbo.myfootball.data.PlayServicesLocationDataSource
import es.santyarbo.myfootball.data.database.FootballDatabase
import es.santyarbo.myfootball.data.database.countries.CountryRoomDataSource
import es.santyarbo.myfootball.data.database.teams.TeamRoomDataSource
import es.santyarbo.myfootball.data.server.countries.CountryApiDataSource
import es.santyarbo.myfootball.data.server.leagues.TheLeagueDbDataSource
import es.santyarbo.myfootball.data.server.teams.TeamApiDataSource
import es.santyarbo.myfootball.ui.countries.CountriesFragment
import es.santyarbo.myfootball.ui.countries.CountriesViewModel
import es.santyarbo.myfootball.ui.favs.FavoritesFragment
import es.santyarbo.myfootball.ui.favs.FavoritesViewModel
import es.santyarbo.myfootball.ui.leagues.LeaguesFragment
import es.santyarbo.myfootball.ui.leagues.LeaguesViewModel
import es.santyarbo.myfootball.ui.teams.detail.TeamDetailFragment
import es.santyarbo.myfootball.ui.teams.detail.TeamDetailViewModel
import es.santyarbo.myfootball.ui.teams.list.TeamsFragment
import es.santyarbo.myfootball.ui.teams.list.TeamsViewModel
import es.santyarbo.usescases.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDI() {
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(listOf(appModule, dataModule, scopesModule))
    }
}

private val appModule = module {
    single { FootballDatabase.build(get()) }
    factory<CountryLocalDatasource> { CountryRoomDataSource(get()) }
    factory<CountryRemoteDatasource> { CountryApiDataSource() }
    factory<LeagueDatasource> { TheLeagueDbDataSource() }
    factory<LocationDatasource> { PlayServicesLocationDataSource(get()) }
    factory<TeamLocalDatasource> { TeamRoomDataSource(get()) }
    factory<TeamRemoteDatasource> { TeamApiDataSource() }
    factory<PermissionChecker> { AndroidPermissionChecker(get()) }
}

private val dataModule = module {
    factory { CountryRepository(get(), get(), get()) }
    factory { LeaguesRepository(get()) }
    factory { RegionRepository(get(), get()) }
    factory { TeamsRepository(get(), get()) }
}

private val scopesModule = module {
    scope(named<CountriesFragment>()) {
        viewModel { CountriesViewModel(get()) }
        scoped { GetCountries(get()) }
    }

    scope(named<FavoritesFragment>()) {
        viewModel { FavoritesViewModel(get()) }
        scoped { GetTeamsFavorites(get()) }
    }

    scope(named<LeaguesFragment>()) {
        viewModel { (id: Int) -> LeaguesViewModel(id, get(), get()) }
        scoped { GetCountry(get()) }
        scoped { GetLeagues(get()) }
    }

    scope(named<TeamsFragment>()) {
        viewModel { (id: Int) -> TeamsViewModel(id, get()) }
        scoped { GetTeamsByLeague(get()) }
    }

    scope(named<TeamDetailFragment>()) {
        viewModel { (id: Int) -> TeamDetailViewModel(id, get(), get()) }
        scoped { FindTeamById(get()) }
        scoped { ToggleTeamFavorite(get()) }
    }

}