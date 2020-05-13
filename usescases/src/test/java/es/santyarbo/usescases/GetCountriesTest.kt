package es.santyarbo.usescases

import com.nhaarman.mockitokotlin2.whenever
import es.santyarbo.data.repository.CountryRepository
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
class GetCountriesTest {

    @Mock
    lateinit var countryRepository: CountryRepository

    lateinit var getCountries: GetCountries

    @Before
    fun setUp() {
        getCountries = GetCountries(countryRepository)
    }

    @Test
    fun `invoke calls countries repository`() {
        runBlocking {
            val countries = ResultWrapper.Success(listOf(mockedCountry.copy(1)))
            whenever(countryRepository.getCountries()).thenReturn(countries)

            val result = getCountries.invoke()

            Assert.assertEquals(countries, result)
        }
    }
}