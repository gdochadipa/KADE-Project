package co.ocha.eplmatch.LastEvent

import co.ocha.eplmatch.Api.ApiRepository
import co.ocha.eplmatch.Api.MatchDBApi
import co.ocha.eplmatch.Model.LastMatch.LastMatchResponse
import co.ocha.eplmatch.NextEvent.ListLeague
import co.ocha.eplmatch.Untils.CoroutinesContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class LastEventPresenter(private val view: LastEventView,
                         private val apiRepository: ApiRepository,
                         private val gson: Gson,
                         private val context: CoroutinesContextProvider = CoroutinesContextProvider()){
    fun getMatchList(idLeague: String) {
        view.showLoading()

            async(context.main) {
                val data = bg {
                    gson.fromJson(apiRepository.doRequest(MatchDBApi.getLastMatch(idLeague)), LastMatchResponse::class.java)
                }
                view.showMatchList(data.await().events)
                view.hideLoading()
            }

    }
}
