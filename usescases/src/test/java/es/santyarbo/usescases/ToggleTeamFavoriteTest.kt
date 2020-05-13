package es.santyarbo.usescases

import com.nhaarman.mockitokotlin2.verify
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
class ToggleTeamFavoriteTest {

    @Mock
    lateinit var teamsRepository: TeamsRepository

    lateinit var toggleTeamFavorite: ToggleTeamFavorite

    @Before
    fun setUp() {
        toggleTeamFavorite = ToggleTeamFavorite(teamsRepository)
    }

    @Test
    fun `invoke calls movies repository`() {
        runBlocking {
            val team = mockedTeam.copy(id = 1)

            val result = toggleTeamFavorite.invoke(team)

            verify(teamsRepository).update(result)
        }
    }

    @Test
    fun `favorite team becomes unfavorite`() {
        runBlocking {
            val team = mockedTeam.copy(favorite = true)

            val result = toggleTeamFavorite.invoke(team)

            Assert.assertFalse(result.favorite)
        }
    }

    @Test
    fun `favorite team becomes favorite`() {
        runBlocking {
            val team = mockedTeam.copy(favorite = false)

            val result = toggleTeamFavorite.invoke(team)

            Assert.assertTrue(result.favorite)
        }
    }
}