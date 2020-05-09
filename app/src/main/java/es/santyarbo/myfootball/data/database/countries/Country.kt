package es.santyarbo.myfootball.data.database.countries

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Country(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val country: String,
    val code: String?,
    val flag: String?
)