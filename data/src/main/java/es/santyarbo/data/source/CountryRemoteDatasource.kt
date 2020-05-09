package es.santyarbo.data.source

import es.santyarbo.domain.Country
import es.santyarbo.domain.ResultWrapper

interface CountryRemoteDatasource {
    suspend fun getCountries() : ResultWrapper<List<Country>>
}