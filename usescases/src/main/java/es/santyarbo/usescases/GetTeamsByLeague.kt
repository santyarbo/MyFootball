package es.santyarbo.usescases

import es.santyarbo.data.repository.TeamsRepository
import es.santyarbo.domain.ResultWrapper
import es.santyarbo.domain.Team

class GetTeamsByLeague(private val teamsRepository: TeamsRepository) {
    suspend fun invoke(leagueId: Int): ResultWrapper<List<Team>> = teamsRepository.getTeamsByLeague(leagueId)
}