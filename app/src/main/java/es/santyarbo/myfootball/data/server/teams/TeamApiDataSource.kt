package es.santyarbo.myfootball.data.server.teams

import es.santyarbo.data.source.TeamRemoteDatasource
import es.santyarbo.domain.Team
import es.santyarbo.myfootball.data.server.common.FootballApi
import es.santyarbo.myfootball.data.toDomainTeam

class TeamApiDataSource : TeamRemoteDatasource {
    override suspend fun getTeamsByLeague(leagueId: Int): List<Team> =
        FootballApi.service
            .getTeams(leagueId.toString())
            .api
            .teams.map { it.toDomainTeam(leagueId) }

}