package es.santyarbo.data.source

import es.santyarbo.domain.Country

interface CountryLocalDatasource {
    suspend fun isEmpty(): Boolean
    suspend fun saveCountries(countries: List<Country>)
    suspend fun getCountries() : List<Country>
    suspend fun findById(id: Int) : Country
    suspend fun update(country: Country)
    suspend fun findByCode(code: String) : Country
}