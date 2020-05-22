package es.santyarbo.myfootball.ui.teams.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import es.santyarbo.data.source.TeamLocalDatasource
import es.santyarbo.domain.Team
import es.santyarbo.myfootball.FakeTeamsLocalDataSource
import es.santyarbo.myfootball.defaultFakeTeams
import es.santyarbo.myfootball.initMockedDi
import es.santyarbo.testshared.mockedTeam
import es.santyarbo.usescases.GetTeamsByLeague
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
class TeamsIntegrationTests : AutoCloseKoinTest(){

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observerTeams: Observer<List<Team>>

    private lateinit var vm: TeamsViewModel

    @Before
    fun setUp() {
        val vmModule = module {
            factory { (id: Int) -> TeamsViewModel(id, get(), get()) }
            factory { GetTeamsByLeague(get()) }
        }

        initMockedDi(vmModule)
        vm = get{ parametersOf(1) }
    }

    @Test
    fun `teams is loaded from server when local source is empty`() {
        vm.teams.observeForever(observerTeams)

        vm.getTeams()

        verify(observerTeams).onChanged(defaultFakeTeams)
    }

    @Test
    fun `teams is loaded from local data source when available`() {
        val fakeLocalTeams = listOf(mockedTeam.copy(10), mockedTeam.copy(14))
        val teamLocalDataSource = get<TeamLocalDatasource>() as FakeTeamsLocalDataSource
        teamLocalDataSource.teams = fakeLocalTeams
        vm.teams.observeForever(observerTeams)

        vm.getTeams()

        verify(observerTeams).onChanged(fakeLocalTeams)
    }

}