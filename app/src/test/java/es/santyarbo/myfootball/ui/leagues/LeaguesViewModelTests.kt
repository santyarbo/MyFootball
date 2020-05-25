package es.santyarbo.myfootball.ui.leagues

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import es.santyarbo.domain.Country
import es.santyarbo.domain.League
import es.santyarbo.domain.ResultWrapper
import es.santyarbo.myfootball.ui.common.ErrorLayout
import es.santyarbo.testshared.mockedCountry
import es.santyarbo.testshared.mockedLeague
import es.santyarbo.usescases.GetCountry
import es.santyarbo.usescases.GetLeagues
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
class LeaguesViewModelTests {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getCountry: GetCountry

    @Mock
    lateinit var getLeagues: GetLeagues

    @Mock
    lateinit var observerLeagues: Observer<List<League>>

    @Mock
    lateinit var observerCountry: Observer<Country>

    @Mock
    lateinit var observerError: Observer<ErrorLayout.ErrorType>

    @Mock
    lateinit var observerLoading: Observer<Boolean>

    private lateinit var vm: LeaguesViewModel

    @Before
    fun setUp() {
        vm = LeaguesViewModel(1, getCountry, getLeagues, Dispatchers.Unconfined)
        vm.country.observeForever(observerCountry)
        vm.leagues.observeForever(observerLeagues)
        vm.error.observeForever(observerError)
        vm.loading.observeForever(observerLoading)
    }

    @Test
    fun testNull() {
        runBlocking {
            whenever(getCountry.invoke(1)).thenReturn(null)
            Assert.assertNotNull(vm.onCoarsePermissionRequested())
        }
    }

    @Test
    fun `success call get leagues by country`() {
        runBlocking {
            val country = mockedCountry.copy(1)
            whenever(getCountry.invoke(1)).thenReturn(country)
            val leagues = listOf(mockedLeague.copy(1), mockedLeague.copy(2))
            whenever(getLeagues.invoke(country)).thenReturn(ResultWrapper.Success(leagues))

            vm.onCoarsePermissionRequested()

            verify(observerLoading).onChanged(true)
            verify(observerCountry).onChanged(country)
            verify(observerLeagues).onChanged(leagues)
            verify(observerLoading).onChanged(false)
        }
    }

    @Test
    fun `error unknown when call leagues by country`() {
        runBlocking {
            val country = mockedCountry.copy(1)
            whenever(getCountry.invoke(1)).thenReturn(country)
            whenever(getLeagues.invoke(country)).thenReturn(ResultWrapper.GenericError())

            vm.onCoarsePermissionRequested()

            verify(observerLoading).onChanged(true)
            verify(observerCountry).onChanged(country)
            verify(observerError).onChanged(ErrorLayout.ErrorType.UNKNOWN)
            verify(observerLoading).onChanged(false)
        }
    }

    @Test
    fun `error network when call leagues by country`() {
        runBlocking {
            val country = mockedCountry.copy(1)
            whenever(getCountry.invoke(1)).thenReturn(country)
            whenever(getLeagues.invoke(country)).thenReturn(ResultWrapper.NetworkError)

            vm.onCoarsePermissionRequested()

            verify(observerLoading).onChanged(true)
            verify(observerCountry).onChanged(country)
            verify(observerError).onChanged(ErrorLayout.ErrorType.NETWORK)
            verify(observerLoading).onChanged(false)
        }
    }
}