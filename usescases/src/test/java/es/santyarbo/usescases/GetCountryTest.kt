package es.santyarbo.usescases

import com.nhaarman.mockitokotlin2.whenever
import es.santyarbo.data.repository.CountryRepository
import es.santyarbo.testshared.mockedCountry
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetCountryTest {
    @Mock
    lateinit var countryRepository: CountryRepository

    lateinit var getCountry: GetCountry

    @Before
    fun setUp() {
        getCountry = GetCountry(countryRepository)
    }

    @Test
    fun `invoke calls country repository`() {
        runBlocking {
            val country = mockedCountry.copy(id = 1)
            whenever(countryRepository.getCountry(1)).thenReturn(country)

            val result = getCountry.invoke(1)

            Assert.assertEquals(country, result)
        }
    }
}