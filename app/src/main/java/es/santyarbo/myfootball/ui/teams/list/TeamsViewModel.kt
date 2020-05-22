package es.santyarbo.myfootball.ui.teams.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.santyarbo.domain.ResultWrapper
import es.santyarbo.domain.Team
import es.santyarbo.myfootball.ui.common.ErrorLayout
import es.santyarbo.myfootball.ui.common.Event
import es.santyarbo.myfootball.ui.common.ScopedViewModel
import es.santyarbo.usescases.GetTeamsByLeague
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class TeamsViewModel (
    private val leagueId: Int,
    private val getTeamsByLeague: GetTeamsByLeague,
    override var uiDispatcher: CoroutineDispatcher) : ScopedViewModel(uiDispatcher) {

    private val _teams = MutableLiveData<List<Team>>()
    val teams: LiveData<List<Team>> get() = _teams

    private val _error = MutableLiveData<ErrorLayout.ErrorType>()
    val error: LiveData<ErrorLayout.ErrorType> get() = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _navigateToDetail = MutableLiveData<Event<Team>>()
    val navigateToDetail: LiveData<Event<Team>> get() = _navigateToDetail

    private val _navigateOnBack = MutableLiveData<Event<Boolean>>()
    val navigateOnBack: LiveData<Event<Boolean>> get() = _navigateOnBack

    fun getTeams() {
        launch {
            _loading.value = true
            when(val result = getTeamsByLeague.invoke(leagueId)) {
                is ResultWrapper.Success -> {
                    if (result.value.isEmpty()) {
                        _error.value = ErrorLayout.ErrorType.NOT_RESULTS
                    }
                    _teams.value = result.value
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

    fun onTeamClicked(team: Team) {
        _navigateToDetail.value = Event(team)
    }

    fun onRetryClicked() {
        getTeams()
    }

    fun onBackClicked() {
        _navigateOnBack.value = Event(true)
    }

}