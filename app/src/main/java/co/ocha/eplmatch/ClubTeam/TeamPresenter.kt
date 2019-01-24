package co.ocha.eplmatch.ClubTeam

import android.content.Context
import co.ocha.eplmatch.Api.ApiRepository
import co.ocha.eplmatch.Api.MatchDBApi
import co.ocha.eplmatch.Model.League.LeaguesResponse
import co.ocha.eplmatch.Model.Team.TeamResponse
import co.ocha.eplmatch.Untils.CoroutinesContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import kotlin.coroutines.experimental.CoroutineContext

class TeamPresenter(private val view: TeamView,
                    private val gson: Gson,
                    private val apiRepository: ApiRepository,
                    private val context: CoroutinesContextProvider= CoroutinesContextProvider()){
    fun getTeamList(league: String){
        view.showLoading()



        async(context.main) {
            val data= bg {
                gson.fromJson(apiRepository.doRequest(MatchDBApi.getTeams(league)),TeamResponse::class.java)

            }
            view.hideLoading()
            view.showTeamList(data.await().teams)
        }
    }

    fun getLeaguelist(){
        val linkLeague = "https://www.thesportsdb.com/api/v1/json/1/all_leagues.php"

        async(context.main) {
            val data=bg{
                gson.fromJson(apiRepository.doRequest(linkLeague),LeaguesResponse::class.java)
            }
            view.showLeague(data.await().leagues)
        }
    }

    fun getSearchTeam(nameTeam: String?){
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository.doRequest(MatchDBApi.getSearchTeam(nameTeam)),TeamResponse::class.java)
            uiThread {
                view.hideLoading()
                view.showTeamList(data.teams)
            }
        }
    }
}