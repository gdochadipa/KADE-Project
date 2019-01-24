package co.ocha.eplmatch.Db.FavoritTeamDB

data class FavoritTeam(val id: Long?, val idTeam: String?, val strTeam: String?, val strTeamBadge: String?){
    companion object {
        const val TABLE_FAVORIT_TEAM: String = "TABLE_FAVORIT_TEAM"
        const val ID:String = "ID"
        const val TEAM_ID:String = "TEAM_ID"
        const val TEAM_NAME:String = "TEAM_NAME"
        const val TEAM_LOGO:String = "TEAM_LOGO"
    }
}