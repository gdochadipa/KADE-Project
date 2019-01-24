package co.ocha.eplmatch.NextEvent

import co.ocha.eplmatch.Api.ApiRepository
import co.ocha.eplmatch.Api.MatchDBApi
import co.ocha.eplmatch.Model.League.LeaguesResponse
import co.ocha.eplmatch.Model.NextMatch.NextMatchResponse
import co.ocha.eplmatch.Model.Search.SearchMatchResponse
import co.ocha.eplmatch.Untils.CoroutinesContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class NextEventPresenter(private val gson: Gson,
                         private val apiRepository: ApiRepository,
                         private val view: NextEventView,
                         private val context:CoroutinesContextProvider = CoroutinesContextProvider()){
    fun nextEventList(idLeague: String){
        view.showLoading()
        async(context.main){
            val data= bg {
                gson.fromJson(apiRepository.doRequest(MatchDBApi.getNextEvent(idLeague)), NextMatchResponse::class.java)
            }
            view.showMatchList(data.await().events)
            view.hideLoading()
        }
    }

    fun searchEventList(strEvent: String?){
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository.doRequest(MatchDBApi.getSearchEvent(strEvent)),SearchMatchResponse::class.java)
            uiThread {

                view.hideLoading()
                view.showEventList(data.event)
            }
        }
    }

    fun getLeaguelist(){
        val linkLeague = "https://www.thesportsdb.com/api/v1/json/1/all_leagues.php"

        doAsync {
            val data=gson.fromJson(apiRepository.doRequest(linkLeague), LeaguesResponse::class.java)
            uiThread {
                view.hideLoading()
                view.showLeague(data.leagues)
            }
        }
    }

}