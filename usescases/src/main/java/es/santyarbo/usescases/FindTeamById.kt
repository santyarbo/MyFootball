package es.santyarbo.usescases

import es.santyarbo.data.repository.TeamsRepository
import es.santyarbo.domain.Team

class FindTeamById (private val teamsRepository: TeamsRepository){
    suspend fun invoke(id: Int): Team = teamsRepository.findById(id)
}