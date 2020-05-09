package es.santyarbo.myfootball.ui.countries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.santyarbo.domain.Country
import es.santyarbo.domain.ResultWrapper
import es.santyarbo.myfootball.R
import es.santyarbo.myfootball.ui.common.ErrorLayout
import es.santyarbo.myfootball.ui.common.Event
import es.santyarbo.myfootball.ui.common.Scope
import es.santyarbo.usescases.GetCountries
import kotlinx.coroutines.launch

class CountriesViewModel(private val getCountries: GetCountries) : ViewModel(),
    Scope by Scope.Impl() {

    private val _countries = MutableLiveData<List<Country>>()
    val countries: LiveData<List<Country>> get() = _countries

    private val _error = MutableLiveData<ErrorLayout.ErrorType>()
    val error: LiveData<ErrorLayout.ErrorType> get() = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _navigateToCountry = MutableLiveData<Event<Int>>()
    val navigateToCountry: LiveData<Event<Int>> get() = _navigateToCountry

    init {
        initScope()
        refresh()
    }

    private fun refresh() {
        launch {
            _loading.value = true

            when(val result = getCountries.invoke()) {
                is ResultWrapper.Success -> {
                    if (result.value.isEmpty()) {
                        _error.value = ErrorLayout.ErrorType.NOT_RESULTS
                    }
                    _countries.value = result.value
                }
                is ResultWrapper.GenericError -> {
                    _error.value = ErrorLayout.ErrorType.UNKNOWN
                }
                is ResultWrapper.NetworkError -> {
                    _error.value = ErrorLayout.ErrorType.NETWORK
                }
            }
            _loading.value = false
        }
    }

    fun onCountryClicked(country: Country) {
        _navigateToCountry.value = Event(country.id)
    }

    fun onRetryClicked() {
        refresh()
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}
