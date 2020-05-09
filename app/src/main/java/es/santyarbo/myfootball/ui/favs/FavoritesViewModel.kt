package es.santyarbo.myfootball.ui.favs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.santyarbo.domain.Team
import es.santyarbo.myfootball.ui.common.Event
import es.santyarbo.myfootball.ui.common.ScopedViewModel
import es.santyarbo.usescases.GetTeamsFavorites
import kotlinx.coroutines.launch

class FavoritesViewModel(private val getTeamsFavorites: GetTeamsFavorites) : ScopedViewModel() {

    private val _teams = MutableLiveData<List<Team>>()
    val teams: LiveData<List<Team>> get() = _teams

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _navigateToDetail = MutableLiveData<Event<Team>>()
    val navigateToDetail: LiveData<Event<Team>> get() = _navigateToDetail

    init {
        refresh()
    }

    private fun refresh() {
        launch {
            _loading.value = true
            _teams.value = getTeamsFavorites.invoke()
            _loading.value = false
        }
    }

    fun onTeamClicked(team: Team) {
        _navigateToDetail.value = Event(team)
    }
}
