package es.santyarbo.myfootball.ui.leagues

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import es.santyarbo.data.source.CountryLocalDatasource
import es.santyarbo.domain.Country
import es.santyarbo.domain.League
import es.santyarbo.myfootball.FakeCountryLocalDataSource
import es.santyarbo.myfootball.defaultFakeLeagues
import es.santyarbo.myfootball.initMockedDi
import es.santyarbo.testshared.mockedCountry
import es.santyarbo.usescases.GetCountry
import es.santyarbo.usescases.GetLeagues
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LeaguesIntegrationTests : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observerLeagues: Observer<List<League>>

    @Mock
    lateinit var observerCountry: Observer<Country>

    private lateinit var vm: LeaguesViewModel

    @Before
    fun setUp() {
        val vmModule = module {
            factory { (id: Int) -> LeaguesViewModel(id, get(), get(), get()) }
            factory { GetCountry(get()) }
            factory { GetLeagues(get()) }
        }

        initMockedDi(vmModule)
    }

    @Test
    fun `get Leagues from remote`() {
        vm = get{ parametersOf(-1)}
        vm.leagues.observeForever(observerLeagues)

        vm.onCoarsePermissionRequested()

        verify(observerLeagues).onChanged(defaultFakeLeagues)
    }

    @Test
    fun `get country by id`() {
        vm = get{ parametersOf(3)}
        val fakeLocalCountries = listOf(mockedCountry.copy(3), mockedCountry.copy(12))
        val countryLocalDataSource = get<CountryLocalDatasource>() as FakeCountryLocalDataSource
        countryLocalDataSource.countries = fakeLocalCountries
        vm.country.observeForever(observerCountry)

        vm.onCoarsePermissionRequested()

        runBlocking {
            verify(observerCountry).onChanged(countryLocalDataSource.findById(3))
        }

    }

    @Test
    fun `get country from local data source by region`() {
        vm = get{ parametersOf(-1)}
        val fakeLocalCountries = listOf(mockedCountry.copy(3, code = "es"), mockedCountry.copy(12, code = "pt"))
        val countryLocalDataSource = get<CountryLocalDatasource>() as FakeCountryLocalDataSource
        countryLocalDataSource.countries = fakeLocalCountries
        vm.country.observeForever(observerCountry)

        vm.onCoarsePermissionRequested()

        runBlocking {
            verify(observerCountry).onChanged(countryLocalDataSource.findByCode("es"))
        }
    }

    @Test
    fun `get country when local data is empty`() {
        vm = get{ parametersOf(-1)}
        val countryLocalDataSource = get<CountryLocalDatasource>() as FakeCountryLocalDataSource
        countryLocalDataSource.countries = emptyList()
        vm.country.observeForever(observerCountry)

        vm.onCoarsePermissionRequested()

        runBlocking {
            verify(observerCountry).onChanged(mockedCountry.copy(-1))
        }
    }


}