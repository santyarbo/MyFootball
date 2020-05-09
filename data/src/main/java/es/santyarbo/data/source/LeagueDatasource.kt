package es.santyarbo.data.source

import es.santyarbo.domain.Country
import es.santyarbo.domain.League
import es.santyarbo.domain.ResultWrapper

interface LeagueDatasource {
    suspend fun findLeagues(country: Country) : ResultWrapper<List<League>>
}