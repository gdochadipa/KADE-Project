package co.ocha.eplmatch.DetailClub.PlayerClub

import co.ocha.eplmatch.Model.Detail.PlayersItem
import co.ocha.eplmatch.Model.Player.PlayerItem

interface ListPlayer {
    fun showLoading()
    fun hideLoading()
    fun showListPlayer(data: List<PlayerItem>)
    fun showDetail(data: List<PlayersItem?>?)
}