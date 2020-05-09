package es.santyarbo.myfootball.ui.teams.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import es.santyarbo.domain.Team
import es.santyarbo.myfootball.ui.common.Event
import es.santyarbo.myfootball.ui.common.ScopedViewModel
import es.santyarbo.usescases.FindTeamById
import es.santyarbo.usescases.ToggleTeamFavorite
import kotlinx.coroutines.launch

class TeamDetailViewModel(
    private val teamId: Int,
    private val findTeamById: FindTeamById,
    private val toggleTeamFavorite: ToggleTeamFavorite
) : ScopedViewModel() {

    private val _team = MutableLiveData<Team>()
    val team: LiveData<Team> get() = _team

    private val _logo = MutableLiveData<String>()
    val logo: LiveData<String> get() = _logo

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> get() = _name

    private val _founded = MutableLiveData<Int>()
    val founded: LiveData<Int> get() = _founded

    private val _stadium = MutableLiveData<String>()
    val stadium: LiveData<String> get() = _stadium

    private val _capacity = MutableLiveData<Int>()
    val capacity: LiveData<Int> get() = _capacity

    private val _surface = MutableLiveData<String>()
    val surface: LiveData<String> get() = _surface

    private val _address = MutableLiveData<String>()
    val address: LiveData<String> get() = _address

    private val _city = MutableLiveData<String>()
    val city: LiveData<String> get() = _city

    private val _favorite = MutableLiveData<Boolean>()
    val favorite: LiveData<Boolean> get() = _favorite

    private val _navigateOnBack = MutableLiveData<Event<Boolean>>()
    val navigateOnBack: LiveData<Event<Boolean>> get() = _navigateOnBack

    init {
        initScope()
        launch {
            _team.value = findTeamById.invoke(teamId)
            updateUi()
        }
    }

    private fun updateUi() {
        team.value?.run {
            _logo.value = logo
            _name.value = name
            _founded.value = founded
            _stadium.value = venueName
            _capacity.value = venueCapacity
            _surface.value = venueSurface
            _address.value = venueAddress
            _city.value = venueCity
            _favorite.value = favorite
        }

    }

    fun onFavoriteClicked() {
        launch {
            team.value?.let {
                _team.value = toggleTeamFavorite.invoke(it)
                updateUi()
            }
        }
    }

    fun onBackClicked() {
        _navigateOnBack.value = Event(true)
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

}
