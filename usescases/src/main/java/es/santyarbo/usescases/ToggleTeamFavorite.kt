package es.santyarbo.usescases

import es.santyarbo.data.repository.TeamsRepository
import es.santyarbo.domain.Team

class ToggleTeamFavorite(private val teamsRepository: TeamsRepository) {
    suspend fun invoke(team: Team) : Team = with(team) {
        copy(favorite = !favorite).also { teamsRepository.update(it)  }
    }
}