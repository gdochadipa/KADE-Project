package co.ocha.eplmatch.Api

import android.net.Uri
import co.ocha.eplmatch.BuildConfig

object MatchDBApi{
    fun getLastMatch(idLeague:String): String {
        val link_last = Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("eventspastleague.php")
                .appendQueryParameter("id",idLeague)
                .build().toString()
        val linked = BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}"+"/eventspastleague.php?id=${idLeague}"
        //"https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=4328"

       return linked
    }

    fun getNextEvent(idLeague:String):String{

        val link_next =  Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("eventsnextleague.php")
                .appendQueryParameter("id",idLeague)
                .build().toString()
        //"https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4328"
        val linkedNext = BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}"+"/eventsnextleague.php?id=${idLeague}"

        return linkedNext
    }

    fun getMatchD(idEvent: String?):String?{
        return "https://www.thesportsdb.com/api/v1/json/1/lookupevent.php?id=$idEvent"
    }

    fun getLookupTeam(idTeam: String?):String{

        return "https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id=$idTeam"
    }

    fun getTeams(league: String):String{

        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("search_all_teams.php")
                .appendQueryParameter("l", league)
                .build()
                .toString()

     // return "https://www.thesportsdb.com/api/v1/json/1/search_all_teams.php?l=Spanish La Liga"
    }



    fun getPlayer(idTeam: String):String{
        return "https://www.thesportsdb.com/api/v1/json/1/lookup_all_players.php?id=$idTeam"
    }

    fun getDetailPlayer(idPlayer: String?):String{
        return "https://www.thesportsdb.com/api/v1/json/1/lookupplayer.php?id=$idPlayer"
    }
    fun getSearchTeam(nameTeam: String?):String{
        val linkDetail = Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("searchteams.php")
                .appendQueryParameter("t",nameTeam)
                .build()
                .toString()

        return linkDetail
    }

    fun getSearchEvent(strEvent: String?):String{
        val link_search = Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("searchevents.php")
                .appendQueryParameter("e",strEvent)
                .build().toString()
        val link2 = "https://www.thesportsdb.com/api/v1/json/1/searchevents.php?e=Arsenal_vs_Chelsea"

        return link_search
    }



}