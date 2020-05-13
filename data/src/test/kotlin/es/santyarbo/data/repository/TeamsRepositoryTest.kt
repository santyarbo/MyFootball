package es.santyarbo.data.repository

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import es.santyarbo.data.source.TeamLocalDatasource
import es.santyarbo.data.source.TeamRemoteDatasource
import es.santyarbo.domain.ResultWrapper
import es.santyarbo.testshared.mockedTeam
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TeamsRepositoryTest {

    @Mock
    lateinit var teamLocalDatasource: TeamLocalDatasource

    @Mock
    lateinit var teamRemoteDatasource: TeamRemoteDatasource

    lateinit var teamsRepository: TeamsRepository

    @Before
    fun setUp() {
        teamsRepository = TeamsRepository(teamLocalDatasource, teamRemoteDatasource)
    }

    @Test
    fun `getTeams from local data source first`() {
        runBlocking {
            val localTeams = ResultWrapper.Success(listOf(mockedTeam.copy(id = 1)))
            whenever(teamLocalDatasource.isEmpty(1)).thenReturn(false)
            whenever(teamLocalDatasource.getTeamsByLeague(1)).thenReturn(localTeams.value)

            val result = teamsRepository.getTeamsByLeague(1)

            Assert.assertEquals(localTeams, result)
        }
    }

    @Test
    fun `getTeams saves remote data to local`() {
        runBlocking {
            val remoteTeams = ResultWrapper.Success(listOf(mockedTeam.copy(id = 2)))
            whenever(teamLocalDatasource.isEmpty(1)).thenReturn(true)
            whenever(teamRemoteDatasource.getTeamsByLeague(1)).thenReturn(remoteTeams)

            teamsRepository.getTeamsByLeague(1)

            verify(teamLocalDatasource).saveTeams(remoteTeams.value, 1)
        }
    }

    @Test
    fun `getTeams network error`() {
        runBlocking {
            val networkError = ResultWrapper.NetworkError
            whenever(teamLocalDatasource.isEmpty(1)).thenReturn(true)
            whenever(teamRemoteDatasource.getTeamsByLeague(1)).thenReturn(networkError)

            val result = teamsRepository.getTeamsByLeague(1)

            Assert.assertEquals(networkError, result)
        }
    }

    @Test
    fun `getTeams generic error`() {
        runBlocking {
            val genericError = ResultWrapper.GenericError()
            whenever(teamLocalDatasource.isEmpty(1)).thenReturn(true)
            whenever(teamRemoteDatasource.getTeamsByLeague(1)).thenReturn(genericError)

            val result = teamsRepository.getTeamsByLeague(1)

            Assert.assertEquals(genericError, result)
        }
    }

    @Test
    fun `team find by id calls local data source`() {
        runBlocking {
            val team = mockedTeam.copy(id = 10)
            whenever(teamLocalDatasource.findById(10)).thenReturn(team)

            val result = teamsRepository.findById(10)

            Assert.assertEquals(team, result)
        }
    }

    @Test
    fun `get teams favorites calls local data source`() {
        runBlocking {
            val teams = listOf(mockedTeam.copy(id = 3))
            whenever(teamLocalDatasource.getTeamsFavorites()).thenReturn(teams)

            val result = teamsRepository.getTeamsFavorites()

            Assert.assertEquals(teams, result)
        }
    }

    @Test
    fun `update team local data source`() {
        runBlocking {
            val team = mockedTeam.copy(id = 1)

            teamsRepository.update(team)

            verify(teamLocalDatasource).update(team)
        }
    }
}