package es.santyarbo.myfootball.data.server.teams

import es.santyarbo.data.source.TeamRemoteDatasource
import es.santyarbo.domain.ResultWrapper
import es.santyarbo.domain.Team
import es.santyarbo.myfootball.data.server.common.FootballApi
import es.santyarbo.myfootball.data.server.common.convertErrorBody
import es.santyarbo.myfootball.data.toDomainTeam
import retrofit2.HttpException
import java.io.IOException

class TeamApiDataSource : TeamRemoteDatasource {
    override suspend fun getTeamsByLeague(leagueId: Int): ResultWrapper<List<Team>> {
       return try {
           val teams = FootballApi.service
               .getTeams(leagueId.toString())
               .api
               .teams.map { it.toDomainTeam(leagueId) }
           return ResultWrapper.Success(teams)
       } catch (throwable: Throwable) {
           when (throwable) {
               is IOException -> ResultWrapper.NetworkError
               is HttpException -> {
                   val code = throwable.code()
                   val errorResponse = convertErrorBody(throwable)
                   ResultWrapper.GenericError(code, errorResponse)
               } else -> ResultWrapper.GenericError(null, null)

           }
       }
    }
    
}