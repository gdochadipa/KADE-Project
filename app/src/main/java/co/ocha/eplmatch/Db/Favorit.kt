package co.ocha.eplmatch.Db

data class Favorit(val id: Long?, val eventId: String?,val strEvent:String?, val strDate: String?,
                   val idHomeTeam:String?, val idAwayTeam:String?, val intHomeScore:String?,
                   val intAwayScore:String?, val strTime:String?, val dateEvent:String?){
    companion object {
        const val TABLE_FAVORIT: String = "TABLE_FAVORIT"
        const val ID: String = "ID_"
        const val EVENT_ID = "EVENT_ID"
        const val STR_EVENT = "STR_EVENT"
        const val STR_DATE = "STR_DATE"
        const val HOME_TEAM_ID = "HOME_TEAM_ID"
        const val AWAY_TEAM_ID = "AWAY_TEAM_ID"
        const val HOME_TEAM_SCR = "HOME_TEAM_SCR"
        const val AWAY_TEAM_SCR = "AWAY_TEAM_SCR"
        const val STR_TIME = "STR_TIME"
        const val DATE_EVENT="DATE_EVENT"
    }
}