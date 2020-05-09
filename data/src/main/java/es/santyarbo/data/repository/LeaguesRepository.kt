package es.santyarbo.data.repository

import es.santyarbo.data.source.LeagueDatasource
import es.santyarbo.domain.Country
import es.santyarbo.domain.League
import es.santyarbo.domain.ResultWrapper

class LeaguesRepository(private val leagueDatasource: LeagueDatasource) {
    suspend fun findLeagues(country: Country) : ResultWrapper<List<League>> {
        return leagueDatasource.findLeagues(country)
    }
}