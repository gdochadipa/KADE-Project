package co.ocha.eplmatch.ClubTeam

import co.ocha.eplmatch.Model.League.LeaguesItem
import co.ocha.eplmatch.Model.Team.TeamsItem

interface TeamView{
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<TeamsItem?>?)
    fun showLeague(league: List<LeaguesItem>)
}