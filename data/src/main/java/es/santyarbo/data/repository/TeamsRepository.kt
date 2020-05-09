package es.santyarbo.data.repository

import es.santyarbo.data.source.TeamLocalDatasource
import es.santyarbo.data.source.TeamRemoteDatasource
import es.santyarbo.domain.ResultWrapper
import es.santyarbo.domain.Team

class TeamsRepository(
    private val teamLocalDatasource: TeamLocalDatasource,
    private val teamRemoteDatasource: TeamRemoteDatasource
) {

    suspend fun getTeamsByLeague(leagueId: Int) : ResultWrapper<List<Team>> {

        if (teamLocalDatasource.isEmpty(leagueId)) {
            when (val response = teamRemoteDatasource.getTeamsByLeague(leagueId)) {
                is ResultWrapper.NetworkError -> return response
                is ResultWrapper.GenericError -> return response
                is ResultWrapper.Success -> teamLocalDatasource.saveTeams(response.value, leagueId)
            }
        }

        return ResultWrapper.Success(teamLocalDatasource.getTeamsByLeague(leagueId))
    }

    suspend fun getTeamsFavorites() : List<Team> = teamLocalDatasource.getTeamsFavorites()

    suspend fun findById(id: Int) : Team = teamLocalDatasource.findById(id)

    suspend fun update(team: Team) = teamLocalDatasource.update(team)


}