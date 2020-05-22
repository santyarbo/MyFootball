package es.santyarbo.myfootball.ui.teams.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import es.santyarbo.data.source.TeamLocalDatasource
import es.santyarbo.domain.Team
import es.santyarbo.myfootball.FakeTeamsLocalDataSource
import es.santyarbo.myfootball.defaultFakeTeams
import es.santyarbo.myfootball.initMockedDi
import es.santyarbo.testshared.mockedTeam
import es.santyarbo.usescases.FindTeamById
import es.santyarbo.usescases.ToggleTeamFavorite
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TeamDetailIntegrationTest :AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observerTeam: Observer<Team>

    private lateinit var vm: TeamDetailViewModel
    private lateinit var teamLocalDataSource: FakeTeamsLocalDataSource

    @Before
    fun setUp() {
        val vmModule = module {
            factory { (id: Int) -> TeamDetailViewModel(id, get(), get(), get()) }
            factory { FindTeamById(get()) }
            factory { ToggleTeamFavorite(get()) }
        }

        initMockedDi(vmModule)
        vm = get { parametersOf(1) }

        teamLocalDataSource = get<TeamLocalDatasource>() as FakeTeamsLocalDataSource
        teamLocalDataSource.teams = defaultFakeTeams
    }

    @Test
    fun `observing LiveData finds the team`() {
        vm.team.observeForever(observerTeam)

        vm.getTeam()

        verify(observerTeam).onChanged(mockedTeam.copy(1))
    }

    @Test
    fun `favorite is updated in local data source`() {
        vm.team.observeForever(observerTeam)

        vm.getTeam()
        vm.onFavoriteClicked()

        runBlocking {
            assertTrue(teamLocalDataSource.findById(1).favorite)
        }
    }

}