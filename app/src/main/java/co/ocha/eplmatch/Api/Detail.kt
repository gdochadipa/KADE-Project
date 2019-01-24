package co.ocha.eplmatch.Api

import android.net.Uri
import co.ocha.eplmatch.BuildConfig
import co.ocha.eplmatch.Model.*
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class Detail(private val apiRepository: ApiRepository,
                private val gson: Gson,
                private val view: ImageClubView){

    fun findLogo(homeTeam: String?,awayTeam:String?){

      //  val link = "https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id=$homeTeam"
        val link = MatchDBApi.getLookupTeam(homeTeam)
       // val link2 = "https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id=$awayTeam"
        val link2 = MatchDBApi.getLookupTeam(awayTeam)

        doAsync {
            val getData1 = gson.fromJson(apiRepository.doRequest(link),ImageResponse::class.java).teams
            val getData2 = gson.fromJson(apiRepository.doRequest(link2),ImageResponse::class.java).teams
            uiThread {
                view.showImage(getData1,getData2)
            }
        }
    }

    fun findDetailMatch(idEvent: String?){
        val linkDetail = MatchDBApi.getMatchD(idEvent)

        doAsync {
            val getDetailMatch = gson.fromJson(apiRepository.doRequest(linkDetail),DetailResponse::class.java)

            uiThread {
                view.showDetail(getDetailMatch.events)
            }
        }
    }

}

interface ImageClubView{
    fun showImage(data1: List<Image>, data2: List<Image>)
    fun showDetail(data: List<DetEventsItem>)
}
