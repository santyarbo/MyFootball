package es.santyarbo.usescases

import es.santyarbo.data.repository.CountryRepository
import es.santyarbo.domain.Country
import es.santyarbo.domain.ResultWrapper

class GetCountries(private val countryRepository: CountryRepository) {
    suspend fun invoke(): ResultWrapper<List<Country>> = countryRepository.getCountries()
}