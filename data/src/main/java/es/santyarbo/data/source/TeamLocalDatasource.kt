package es.santyarbo.data.source

import es.santyarbo.domain.Team

interface TeamLocalDatasource {
    suspend fun isEmpty(leagueId: Int): Boolean
    suspend fun saveTeams(teams: List<Team>, leagueId: Int)
    suspend fun getTeamsByLeague(leagueId: Int) : List<Team>
    suspend fun getTeamsFavorites() : List<Team>
    suspend fun findById(id: Int) : Team
    suspend fun update(team: Team)
}