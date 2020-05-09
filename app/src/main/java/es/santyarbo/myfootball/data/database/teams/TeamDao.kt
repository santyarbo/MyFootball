package es.santyarbo.myfootball.data.database.teams

import androidx.room.*

@Dao
interface TeamDao {
    @Query("SELECT * FROM Team")
    fun getAll(): List<Team>

    @Query("SELECT * FROM Team WHERE leagueId = :leagueId")
    fun getTeamsByLeague(leagueId: Int): List<Team>

    @Query("SELECT * FROM Team WHERE favorite = 1")
    fun getFavorites(): List<Team>

    @Query("SELECT * FROM Team WHERE id = :id")
    fun findById(id: Int): Team

    @Query("SELECT COUNT(id) FROM Team WHERE leagueId = :leagueId")
    fun teamsCount(leagueId: Int): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTeams(teams: List<Team>)

    @Update
    fun updateTeam(team: Team)
}