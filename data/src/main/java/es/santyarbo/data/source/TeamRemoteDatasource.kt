package es.santyarbo.data.source

import es.santyarbo.domain.ResultWrapper
import es.santyarbo.domain.Team

interface TeamRemoteDatasource {
    suspend fun getTeamsByLeague(leagueId: Int) : ResultWrapper<List<Team>>
}