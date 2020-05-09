package es.santyarbo.usescases

import es.santyarbo.data.repository.CountryRepository
import es.santyarbo.domain.Country

class GetCountry(private val countryRepository: CountryRepository) {
    suspend fun invoke(countryId: Int): Country = countryRepository.getCountry(countryId)
}
