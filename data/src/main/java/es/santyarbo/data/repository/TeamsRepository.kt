package es.santyarbo.data.repository

import es.santyarbo.data.source.TeamLocalDatasource
import es.santyarbo.data.source.TeamRemoteDatasource
import es.santyarbo.domain.Team

class TeamsRepository(
    private val teamLocalDatasource: TeamLocalDatasource,
    private val teamRemoteDatasource: TeamRemoteDatasource
) {

    suspend fun getTeamsByLeague(leagueId: Int) : List<Team> {

        if (teamLocalDatasource.isEmpty(leagueId)) {
            val teams = teamRemoteDatasource.getTeamsByLeague(leagueId)
            teamLocalDatasource.saveTeams(teams, leagueId)
        }

        return teamLocalDatasource.getTeamsByLeague(leagueId)
    }

    suspend fun getTeamsFavorites() : List<Team> = teamLocalDatasource.getTeamsFavorites()

    suspend fun findById(id: Int) : Team = teamLocalDatasource.findById(id)

    suspend fun update(team: Team) = teamLocalDatasource.update(team)


}