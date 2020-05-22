package es.santyarbo.myfootball.ui.countries

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import es.santyarbo.data.source.CountryLocalDatasource
import es.santyarbo.domain.Country
import es.santyarbo.myfootball.FakeCountryLocalDataSource
import es.santyarbo.myfootball.defaultFakeCountries
import es.santyarbo.myfootball.initMockedDi
import es.santyarbo.testshared.mockedCountry
import es.santyarbo.usescases.GetCountries
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CountriesIntegrationTests : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observerCountries: Observer<List<Country>>

    private lateinit var vm: CountriesViewModel

    @Before
    fun setUp() {
        val vmModule = module {
            factory { CountriesViewModel(get(), get()) }
            factory { GetCountries(get()) }
        }

        initMockedDi(vmModule)
        vm = get()
    }

    @Test
    fun `countries is loaded from server when local source is empty`() {
        vm.countries.observeForever(observerCountries)

        vm.getCountries()

        verify(observerCountries).onChanged(defaultFakeCountries)
    }

    @Test
    fun `countries is loaded from local data source when available`() {
        val fakeLocalCountries = listOf(mockedCountry.copy(10), mockedCountry.copy(11))
        val countryLocalDataSource = get<CountryLocalDatasource>() as FakeCountryLocalDataSource
        countryLocalDataSource.countries = fakeLocalCountries
        vm.countries.observeForever(observerCountries)

        vm.getCountries()

        verify(observerCountries).onChanged(fakeLocalCountries)
    }
}

