package co.ocha.eplmatch.DetailClub

import co.ocha.eplmatch.Api.ApiRepository
import co.ocha.eplmatch.Api.ImageClubView
import co.ocha.eplmatch.Api.MatchDBApi
import co.ocha.eplmatch.Model.Team.TeamResponse
import co.ocha.eplmatch.Model.Team.TeamsItem
import co.ocha.eplmatch.Untils.CoroutinesContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailClubPresenter (private val apiRepository: ApiRepository,
                           private val gson: Gson,
                           private val view: ClubView,
                           private val context: CoroutinesContextProvider = CoroutinesContextProvider()){
    fun findDetailMatch(idTeam: String?){
        val linkDetail = MatchDBApi.getLookupTeam(idTeam)

//        doAsync {
//            val getDetail = gson.fromJson(apiRepository.doRequest(linkDetail),TeamResponse::class.java)
//
//            uiThread {
//                view.showDetail(getDetail.teams)
//            }
//        }
        async(context.main) {
            val data= bg {
                gson.fromJson(apiRepository.doRequest(linkDetail),TeamResponse::class.java)
            }
            view.showDetail(data.await().teams)
        }
    }

}

interface ClubView{
    fun showDetail(data: List<TeamsItem?>?)
}