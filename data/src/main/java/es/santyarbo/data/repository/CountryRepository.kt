package es.santyarbo.data.repository

import es.santyarbo.data.source.CountryLocalDatasource
import es.santyarbo.data.source.CountryRemoteDatasource
import es.santyarbo.domain.Country
import es.santyarbo.domain.ResultWrapper

class CountryRepository(
    private val countryRemoteDatasource: CountryRemoteDatasource,
    private val countryLocalDatasource: CountryLocalDatasource,
    private val regionRepository: RegionRepository
) {
    suspend fun getCountries() : ResultWrapper<List<Country>> {
        if (countryLocalDatasource.isEmpty()) {
            when(val response = countryRemoteDatasource.getCountries()) {
                is ResultWrapper.NetworkError -> return response
                is ResultWrapper.GenericError -> return response
                is ResultWrapper.Success -> {
                    countryLocalDatasource.saveCountries(response.value)
                }
            }
        }
        return ResultWrapper.Success(countryLocalDatasource.getCountries())
    }


   suspend fun getCountry(countryId: Int) : Country {
       return if (countryId == -1) {
           if (countryLocalDatasource.isEmpty()) {
               Country(-1, "España", "es", null)
           } else {
               val region = regionRepository.findLastRegion()
               countryLocalDatasource.findByCode(region)
           }
       } else {
           countryLocalDatasource.findById(countryId);
       }

   }

}