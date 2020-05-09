package es.santyarbo.myfootball.data.database.teams

import es.santyarbo.data.source.TeamLocalDatasource
import es.santyarbo.domain.Team
import es.santyarbo.myfootball.data.database.FootballDatabase
import es.santyarbo.myfootball.data.toDomainTeam
import es.santyarbo.myfootball.data.toRoomTeam
import es.santyarbo.myfootball.data.toRoomUpdateTeam
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TeamRoomDataSource(db: FootballDatabase) : TeamLocalDatasource {

    private val teamDao = db.teamDao()

    override suspend fun isEmpty(leagueId: Int): Boolean =
        withContext(Dispatchers.IO) { teamDao.teamsCount(leagueId) <= 0 }

    override suspend fun saveTeams(teams: List<Team>, leagueId: Int) =
        withContext(Dispatchers.IO) { teamDao.insertTeams(teams.map { it.toRoomTeam() }) }


    override suspend fun getTeamsByLeague(leagueId: Int): List<Team> = withContext(Dispatchers.IO) {
        teamDao.getTeamsByLeague(leagueId).map { it.toDomainTeam() }
    }


    override suspend fun getTeamsFavorites(): List<Team> = withContext(Dispatchers.IO) {
        teamDao.getFavorites().map { it.toDomainTeam() }
    }

    override suspend fun findById(id: Int): Team = withContext(Dispatchers.IO) {
        teamDao.findById(id).toDomainTeam()
    }

    override suspend fun update(team: Team) = withContext(Dispatchers.IO) {
        teamDao.updateTeam(team.toRoomUpdateTeam())
    }
}