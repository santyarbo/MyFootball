package es.santyarbo.myfootball.data.server.leagues

import es.santyarbo.data.source.LeagueDatasource
import es.santyarbo.domain.Country
import es.santyarbo.domain.League
import es.santyarbo.domain.ResultWrapper
import es.santyarbo.myfootball.data.server.common.FootballApi
import es.santyarbo.myfootball.data.server.common.convertErrorBody
import es.santyarbo.myfootball.data.toDomainLeague
import retrofit2.HttpException
import java.io.IOException

class TheLeagueDbDataSource : LeagueDatasource {
    override suspend fun findLeagues(country: Country): ResultWrapper<List<League>> {
        country.code?.let { code ->
            return try {
                val leagues = FootballApi.service.getLeagues(code)
                ResultWrapper.Success(leagues.api.leagues.map { it.toDomainLeague() })
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> ResultWrapper.NetworkError
                    is HttpException -> {
                        val errorResponse = convertErrorBody(throwable)
                        ResultWrapper.GenericError(throwable.code(), errorResponse)
                    }
                    else -> ResultWrapper.GenericError(null, null)
                }
            }
        }
        return ResultWrapper.Success(emptyList())
    }


}