package es.santyarbo.usescases

import com.nhaarman.mockitokotlin2.whenever
import es.santyarbo.data.repository.TeamsRepository
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
class GetTeamsByLeagueTest {

    @Mock
    lateinit var teamsRepository: TeamsRepository

    lateinit var getTeamsByLeague: GetTeamsByLeague

    @Before
    fun setUp() {
        getTeamsByLeague = GetTeamsByLeague(teamsRepository)
    }

    @Test
    fun `invoke calls teams repository`() {
        runBlocking {
            val teams = ResultWrapper.Success(listOf(mockedTeam.copy(1)))
            whenever(teamsRepository.getTeamsByLeague(1)).thenReturn(teams)

            val result = getTeamsByLeague.invoke(1)

            Assert.assertEquals(teams, result)
        }
    }
}