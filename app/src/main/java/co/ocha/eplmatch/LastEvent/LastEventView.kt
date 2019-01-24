package co.ocha.eplmatch.LastEvent

import co.ocha.eplmatch.Model.LastMatch.LastEventsItem
import co.ocha.eplmatch.Model.League.LeaguesItem


interface LastEventView{
    fun showLoading()
    fun hideLoading()
    fun showMatchList(data: List<LastEventsItem>)

}