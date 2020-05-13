package es.santyarbo.data.repository

import com.nhaarman.mockitokotlin2.whenever
import es.santyarbo.data.source.LeagueDatasource
import es.santyarbo.domain.ResultWrapper
import es.santyarbo.testshared.mockedCountry
import es.santyarbo.testshared.mockedLeague
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LeaguesRepositoryTest {

    @Mock
    lateinit var leagueDatasource: LeagueDatasource

    lateinit var leaguesRepository: LeaguesRepository

    @Before
    fun setUp() {
        leaguesRepository = LeaguesRepository(leagueDatasource)
    }

    @Test
    fun `getLeagues from remote`() {
        runBlocking {
            val remoteLeagues = ResultWrapper.Success(listOf(mockedLeague.copy(id = 1)))
            whenever(leagueDatasource.findLeagues(mockedCountry)).thenReturn(remoteLeagues)

            val result = leaguesRepository.findLeagues(mockedCountry)

            Assert.assertEquals(remoteLeagues, result)
        }
    }

    @Test
    fun `getLeagues network error`() {
        runBlocking {
            val networkError = ResultWrapper.NetworkError
            whenever(leagueDatasource.findLeagues(mockedCountry)).thenReturn(networkError)

            val result = leaguesRepository.findLeagues(mockedCountry)

            Assert.assertEquals(networkError, result)
        }
    }

    @Test
    fun `getLeagues generic error`() {
        runBlocking {
            val genericError = ResultWrapper.GenericError()
            whenever(leagueDatasource.findLeagues(mockedCountry)).thenReturn(genericError)

            val result = leaguesRepository.findLeagues(mockedCountry)

            Assert.assertEquals(genericError, result)
        }
    }
}