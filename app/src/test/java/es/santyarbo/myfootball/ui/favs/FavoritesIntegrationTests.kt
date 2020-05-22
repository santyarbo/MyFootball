package es.santyarbo.myfootball.ui.favs

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import es.santyarbo.data.source.TeamLocalDatasource
import es.santyarbo.domain.Team
import es.santyarbo.myfootball.FakeTeamsLocalDataSource
import es.santyarbo.myfootball.initMockedDi
import es.santyarbo.testshared.mockedTeam
import es.santyarbo.usescases.GetTeamsFavorites
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoritesIntegrationTests : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observerTeams: Observer<List<Team>>

    private lateinit var vm: FavoritesViewModel

    @Before
    fun setUp() {
        val vmModule = module {
            factory { FavoritesViewModel(get(), get()) }
            factory { GetTeamsFavorites(get()) }
        }

        initMockedDi(vmModule)
        vm = get()
    }

    @Test
    fun `teams favorites is loaded from local data source `() {
        val fakeLocalTeams =
            listOf(mockedTeam.copy(10, favorite = true), mockedTeam.copy(11, favorite = true))
        val teamsLocalDataSource = get<TeamLocalDatasource>() as FakeTeamsLocalDataSource
        teamsLocalDataSource.teams = fakeLocalTeams

        vm.teams.observeForever(observerTeams)

        vm.getTeamsFavorites()

        verify(observerTeams).onChanged(fakeLocalTeams)
    }

    @Test
    fun `teams favorites is empty`() {
        vm.teams.observeForever(observerTeams)

        vm.getTeamsFavorites()

        verify(observerTeams).onChanged(emptyList())
    }
}