package es.santyarbo.usescases

import com.nhaarman.mockitokotlin2.whenever
import es.santyarbo.data.repository.TeamsRepository
import es.santyarbo.testshared.mockedTeam
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetTeamsFavoritesTest {

    @Mock
    lateinit var teamsRepository: TeamsRepository

    lateinit var getTeamsFavorites: GetTeamsFavorites

    @Before
    fun setUp() {
        getTeamsFavorites = GetTeamsFavorites(teamsRepository)
    }

    @Test
    fun `invoke favorites teams`() {
        runBlocking {
            val teams = listOf(mockedTeam.copy(1))
            whenever(teamsRepository.getTeamsFavorites()).thenReturn(teams)

            val result = getTeamsFavorites.invoke()

            Assert.assertEquals(teams, result)
        }
    }
}