package es.santyarbo.usescases

import es.santyarbo.data.repository.LeaguesRepository
import es.santyarbo.domain.Country
import es.santyarbo.domain.League
import es.santyarbo.domain.ResultWrapper

class GetLeagues(private val leaguesRepository: LeaguesRepository) {
    suspend fun invoke(country: Country) : ResultWrapper<List<League>> = leaguesRepository.findLeagues(country)
}