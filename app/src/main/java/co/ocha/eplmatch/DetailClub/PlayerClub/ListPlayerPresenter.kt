package co.ocha.eplmatch.DetailClub.PlayerClub

import co.ocha.eplmatch.Api.ApiRepository
import co.ocha.eplmatch.Api.MatchDBApi
import co.ocha.eplmatch.Model.Detail.DetailResponse
import co.ocha.eplmatch.Model.Player.PlayerResponse
import co.ocha.eplmatch.Untils.CoroutinesContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class ListPlayerPresenter(private val apiRepository: ApiRepository,
                          private val gson: Gson,
                          private val view: ListPlayer,
                          private val context: CoroutinesContextProvider = CoroutinesContextProvider()){
    fun getPlayers(idTeam: String){
        view.showLoading()

        async(context.main) {
            val data= bg {
                gson.fromJson(apiRepository.doRequest(MatchDBApi.getPlayer(idTeam)),PlayerResponse::class.java)
            }

            view.showListPlayer(data.await().player)
            view.hideLoading()
        }
    }

    fun getDetailPlayer(idPlayer: String?) {
        var idTeam = "133604"
        doAsync {
            val data = gson.fromJson(apiRepository.doRequest(MatchDBApi.getDetailPlayer(idPlayer)),DetailResponse::class.java)
            uiThread {

                view.showDetail(data.players)
            }
        }
    }


}