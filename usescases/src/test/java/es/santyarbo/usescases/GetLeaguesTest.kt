package es.santyarbo.usescases

import com.nhaarman.mockitokotlin2.whenever
import es.santyarbo.data.repository.LeaguesRepository
import es.santyarbo.domain.ResultWrapper
import es.santyarbo.testshared.mockedCountry
import es.santyarbo.testshared.mockedLeague
import es.santyarbo.testshared.mockedTeam
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetLeaguesTest {

    @Mock
    lateinit var leaguesRepository: LeaguesRepository

    lateinit var getLeagues: GetLeagues

    @Before
    fun setUp() {
        getLeagues = GetLeagues(leaguesRepository)
    }

    @Test
    fun `invoke calls leagues repository`(){
        runBlocking {
            val leagues = ResultWrapper.Success(listOf(mockedLeague.copy(id = 1)))
            whenever(leaguesRepository.findLeagues(mockedCountry)).thenReturn(leagues)

            val result = getLeagues.invoke(mockedCountry)

            Assert.assertEquals(leagues, result)
        }
    }
}