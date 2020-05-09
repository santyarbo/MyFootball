package es.santyarbo.myfootball.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import es.santyarbo.myfootball.data.database.countries.Country
import es.santyarbo.myfootball.data.database.countries.CountryDao
import es.santyarbo.myfootball.data.database.teams.Team
import es.santyarbo.myfootball.data.database.teams.TeamDao

@Database(entities = [Team::class, Country::class], version = 1, exportSchema = false)
abstract class FootballDatabase : RoomDatabase() {

    companion object {
        fun build(context: Context) = Room.databaseBuilder(
            context,
            FootballDatabase::class.java, "football-db"
        ).build()
    }

    abstract fun teamDao() : TeamDao
    abstract fun countryDao() : CountryDao
}