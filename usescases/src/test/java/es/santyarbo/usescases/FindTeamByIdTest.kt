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
class FindTeamByIdTest {

    @Mock
    lateinit var teamsRepository: TeamsRepository

    lateinit var findTeamById: FindTeamById

    @Before
    fun setUp() {
        findTeamById = FindTeamById(teamsRepository)
    }

    @Test
    fun `invoke calls teams repository`() {
        runBlocking {

            val team = mockedTeam.copy(id = 1)
            whenever(teamsRepository.findById(1)).thenReturn(team)

            val result = findTeamById.invoke(1)

            Assert.assertEquals(team, result)
        }
    }
}