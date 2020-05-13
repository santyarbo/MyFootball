package es.santyarbo.data.repository

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import es.santyarbo.data.source.CountryLocalDatasource
import es.santyarbo.data.source.CountryRemoteDatasource
import es.santyarbo.domain.ResultWrapper
import es.santyarbo.testshared.mockedCountry
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class CountryRepositoryTest {

    @Mock
    lateinit var countryRemoteDatasource: CountryRemoteDatasource

    @Mock
    lateinit var countryLocalDatasource: CountryLocalDatasource

    @Mock
    lateinit var regionRepository: RegionRepository

    lateinit var countryRepository: CountryRepository

    @Before
    fun setUp() {
        countryRepository =
            CountryRepository(countryRemoteDatasource, countryLocalDatasource, regionRepository)
    }

    @Test
    fun `getCountries from local data source first`() {
        runBlocking {
            val localCountries = ResultWrapper.Success(listOf(mockedCountry.copy(id = 1)))
            whenever(countryLocalDatasource.isEmpty()).thenReturn(false)
            whenever(countryLocalDatasource.getCountries()).thenReturn(localCountries.value)

            val result = countryRepository.getCountries()

            Assert.assertEquals(localCountries, result)
        }
    }

    @Test
    fun `getCountries saves remote data to local`() {
        runBlocking {
            val remoteCountries = ResultWrapper.Success(listOf(mockedCountry.copy(2)))
            whenever(countryLocalDatasource.isEmpty()).thenReturn(true)
            whenever(countryRemoteDatasource.getCountries()).thenReturn(remoteCountries)

            countryRepository.getCountries()

            verify(countryLocalDatasource).saveCountries(remoteCountries.value)
        }
    }

    @Test
    fun `getCountries network error`() {
        runBlocking {
            val networkError = ResultWrapper.NetworkError
            whenever(countryLocalDatasource.isEmpty()).thenReturn(true)
            whenever(countryRemoteDatasource.getCountries()).thenReturn(networkError)

            val result = countryRepository.getCountries()

            Assert.assertEquals(networkError, result)
        }
    }

    @Test
    fun `getCountries generic error`() {
        runBlocking {
            val genericError = ResultWrapper.GenericError()
            whenever(countryLocalDatasource.isEmpty()).thenReturn(true)
            whenever(countryRemoteDatasource.getCountries()).thenReturn(genericError)

            val result = countryRepository.getCountries()

            Assert.assertEquals(genericError, result)
        }
    }

    @Test
    fun `getCountry findById calls local data source`() {
        runBlocking {
            val country = mockedCountry.copy(id = 5)
            whenever(countryLocalDatasource.findById(5)).thenReturn(country)

            val result = countryRepository.getCountry(5)

            Assert.assertEquals(country, result)
        }
    }

    @Test
    fun `getCountry without id by region calls local data source`() {
        runBlocking {
            val country = mockedCountry.copy(code = "es")
            whenever(countryLocalDatasource.isEmpty()).thenReturn(false)
            whenever(regionRepository.findLastRegion()).thenReturn("es")
            whenever(countryLocalDatasource.findByCode("es")).thenReturn(country)

            val result = countryRepository.getCountry(-1)

            Assert.assertEquals(country, result)
        }
    }

    @Test
    fun `getCountry without id by default calls local data source`() {
        runBlocking {
            val country = mockedCountry.copy(id = -1)
            whenever(countryLocalDatasource.isEmpty()).thenReturn(true)

            val result = countryRepository.getCountry(-1)

            Assert.assertEquals(country, result)
        }
    }
}