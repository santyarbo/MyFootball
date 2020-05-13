package es.santyarbo.myfootball.data.database.countries

import es.santyarbo.data.source.CountryLocalDatasource
import es.santyarbo.domain.Country
import es.santyarbo.myfootball.data.database.FootballDatabase
import es.santyarbo.myfootball.data.toDomainCountry
import es.santyarbo.myfootball.data.toRoomCountry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CountryRoomDataSource(db: FootballDatabase) : CountryLocalDatasource {

    private val countryDao = db.countryDao()


    override suspend fun isEmpty(): Boolean =
        withContext(Dispatchers.IO) { countryDao.countriesCount() <= 0 }


    override suspend fun saveCountries(countries: List<Country>) =
        withContext(Dispatchers.IO) { countryDao.insertCountries(countries = countries.map { it.toRoomCountry() }) }


    override suspend fun getCountries(): List<Country> =
        withContext(Dispatchers.IO) { countryDao.getAll().map { it.toDomainCountry() } }


    override suspend fun findById(id: Int): Country =
        withContext(Dispatchers.IO) { countryDao.findById(id).toDomainCountry() }


    override suspend fun update(country: Country) =
        withContext(Dispatchers.IO) { countryDao.updateCountry(country.toRoomCountry()) }

    override suspend fun findByCode(code: String): Country =
        withContext(Dispatchers.IO) { countryDao.findByCode(code).toDomainCountry() }

}