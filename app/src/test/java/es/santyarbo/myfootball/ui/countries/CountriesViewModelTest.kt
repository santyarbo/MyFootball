package es.santyarbo.myfootball.ui.countries

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import es.santyarbo.domain.Country
import es.santyarbo.domain.ErrorResponse
import es.santyarbo.domain.ResultWrapper
import es.santyarbo.myfootball.defaultFakeCountries
import es.santyarbo.myfootball.ui.common.ErrorLayout
import es.santyarbo.testshared.mockedCountry
import es.santyarbo.usescases.GetCountries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CountriesViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getCountries: GetCountries

    @Mock
    lateinit var observerCountries: Observer<List<Country>>

    @Mock
    lateinit var observerLoading: Observer<Boolean>

    @Mock
    lateinit var observerError: Observer<ErrorLayout.ErrorType>

    private lateinit var vm: CountriesViewModel

    @Before
    fun setUp() {
        vm = CountriesViewModel(getCountries, Dispatchers.Unconfined)
        vm.countries.observeForever(observerCountries)
        vm.error.observeForever(observerError)
        vm.loading.observeForever(observerLoading)
    }

    @Test
    fun testNull() {
        runBlocking {
            whenever(getCountries.invoke()).thenReturn(null)
            Assert.assertNotNull(vm.getCountries())
        }
    }

    @Test
    fun `error unknown when call getCountries`() {
        runBlocking {
            whenever(getCountries.invoke()).thenReturn(ResultWrapper.GenericError())

            vm.getCountries()

            verify(observerLoading).onChanged(true)
            verify(observerError).onChanged(ErrorLayout.ErrorType.UNKNOWN)
            verify(observerLoading).onChanged(false)
        }
    }

    @Test
    fun `error network when call getCountries`() {
        runBlocking {
            whenever(getCountries.invoke()).thenReturn(ResultWrapper.NetworkError)

            vm.getCountries()

            verify(observerLoading).onChanged(true)
            verify(observerError).onChanged(ErrorLayout.ErrorType.NETWORK)
            verify(observerLoading).onChanged(false)
        }
    }

    @Test
    fun `success call getCountries`() {
        runBlocking {
            val countries = listOf(mockedCountry.copy(1))
            whenever(getCountries.invoke()).thenReturn(ResultWrapper.Success(countries))

            vm.getCountries()

            verify(observerLoading).onChanged(true)
            verify(observerCountries).onChanged(countries)
            verify(observerLoading).onChanged(false)
        }
    }
}