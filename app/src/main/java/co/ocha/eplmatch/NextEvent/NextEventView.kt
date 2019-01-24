package co.ocha.eplmatch.NextEvent



import co.ocha.eplmatch.Model.League.LeaguesItem
import co.ocha.eplmatch.Model.NextMatch.NextEventsItem
import co.ocha.eplmatch.Model.Search.EventItem

interface NextEventView{
    fun showLoading()
    fun hideLoading()
    fun showMatchList(data: List<NextEventsItem?>?)
    fun showEventList(data2: List<EventItem?>?)
    fun showLeague(league: List<LeaguesItem>)
}