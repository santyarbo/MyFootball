package es.santyarbo.usescases

import es.santyarbo.data.repository.TeamsRepository
import es.santyarbo.domain.Team

class GetTeamsFavorites(private val teamsRepository: TeamsRepository) {
    suspend fun invoke(): List<Team> = teamsRepository.getTeamsFavorites()
}