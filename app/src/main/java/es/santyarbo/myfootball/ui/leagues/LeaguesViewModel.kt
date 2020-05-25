package es.santyarbo.myfootball.ui.leagues

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.santyarbo.domain.Country
import es.santyarbo.domain.League
import es.santyarbo.domain.ResultWrapper
import es.santyarbo.myfootball.ui.common.ErrorLayout
import es.santyarbo.myfootball.ui.common.Event
import es.santyarbo.myfootball.ui.common.Scope
import es.santyarbo.myfootball.ui.common.ScopedViewModel
import es.santyarbo.usescases.GetCountry
import es.santyarbo.usescases.GetLeagues
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class LeaguesViewModel(
    private val countryId: Int,
    private val getCountry: GetCountry,
    private val getLeagues: GetLeagues,
    override var uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

    private val _leagues = MutableLiveData<List<League>>()
    val leagues: LiveData<List<League>> get() = _leagues

    private val _country = MutableLiveData<Country>()
    val country: LiveData<Country> get() = _country

    private val _error = MutableLiveData<ErrorLayout.ErrorType>()
    val error: LiveData<ErrorLayout.ErrorType> get() = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _navigateToLeague = MutableLiveData<Event<Int>>()
    val navigateToLeague: LiveData<Event<Int>> get() = _navigateToLeague

    private val _requestLocationPermission = MutableLiveData<Event<Unit>>()
    val requestLocationPermission: LiveData<Event<Unit>> get() = _requestLocationPermission

    fun onCoarsePermissionRequested() {
        launch {
            _loading.value = true
            _country.value = getCountry.invoke(countryId)
            country.value?.let {
                when(val result = getLeagues.invoke(it)) {
                    is ResultWrapper.Success -> {
                        if (result.value.isEmpty()) {
                            _error.value = ErrorLayout.ErrorType.NOT_RESULTS
                        }
                        _leagues.value = result.value
                    }
                    is ResultWrapper.GenericError -> {
                        _error.value = ErrorLayout.ErrorType.UNKNOWN
                    }
                    is ResultWrapper.NetworkError -> {
                        _error.value = ErrorLayout.ErrorType.NETWORK
                    }
                }
            }
            _loading.value = false
        }
    }

    fun onLeagueClicked(league: League) {
        _navigateToLeague.value = Event(league.leagueId)
    }

    fun onRetryClicked() {
        onCoarsePermissionRequested()
    }
}
