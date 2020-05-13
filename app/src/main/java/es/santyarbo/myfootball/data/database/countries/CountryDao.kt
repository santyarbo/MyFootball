package es.santyarbo.myfootball.data.database.countries

import androidx.room.*

@Dao
interface CountryDao {
    @Query("SELECT * FROM Country")
    fun getAll(): List<Country>

    @Query("SELECT * FROM Country WHERE id = :id")
    fun findById(id: Int): Country

    @Query("SELECT * FROM Country WHERE code = :code")
    fun findByCode(code: String): Country

    @Query("SELECT COUNT(id) FROM Country")
    fun countriesCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCountries(countries: List<Country>)

    @Update
    fun updateCountry(country: Country)
}