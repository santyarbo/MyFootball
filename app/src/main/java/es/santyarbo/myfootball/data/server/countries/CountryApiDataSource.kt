package es.santyarbo.myfootball.data.server.countries

import es.santyarbo.data.source.CountryRemoteDatasource
import es.santyarbo.domain.Country
import es.santyarbo.domain.ResultWrapper
import es.santyarbo.myfootball.data.server.common.FootballApi
import es.santyarbo.myfootball.data.server.common.convertErrorBody
import es.santyarbo.myfootball.data.toDomainCountry
import retrofit2.HttpException
import java.io.IOException

class CountryApiDataSource : CountryRemoteDatasource {
    override suspend fun getCountries(): ResultWrapper<List<Country>> {
        return try {
            val countries = FootballApi.service.getCountries().api.countries
            return ResultWrapper.Success(countries.map {  it.toDomainCountry()})
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

