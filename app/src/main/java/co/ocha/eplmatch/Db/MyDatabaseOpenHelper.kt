package co.ocha.eplmatch.Db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import co.ocha.eplmatch.Db.FavoritTeamDB.FavoritTeam
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context): ManagedSQLiteOpenHelper(ctx,"FavoritEvent.db",null,2){

    companion object {
        private var instance: ManagedSQLiteOpenHelper? = null

        @Synchronized
        fun getInstance(ctx : Context): MyDatabaseOpenHelper{
            if (instance == null){
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(p0: SQLiteDatabase) {
        p0.createTable(Favorit.TABLE_FAVORIT,true,
                Favorit.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Favorit.EVENT_ID to TEXT + UNIQUE,
                Favorit.STR_EVENT to TEXT,
                Favorit.STR_DATE to TEXT,
                Favorit.HOME_TEAM_ID to TEXT,
                Favorit.AWAY_TEAM_ID to  TEXT,
                Favorit.HOME_TEAM_SCR to TEXT,
                Favorit.AWAY_TEAM_SCR to TEXT,
                Favorit.STR_TIME to TEXT,
                Favorit.DATE_EVENT to TEXT)

        p0.createTable(FavoritTeam.TABLE_FAVORIT_TEAM, true,
                FavoritTeam.ID to INTEGER+ PRIMARY_KEY+ AUTOINCREMENT,
                FavoritTeam.TEAM_ID to TEXT + UNIQUE,
                FavoritTeam.TEAM_NAME to TEXT,
                FavoritTeam.TEAM_LOGO to TEXT)
    }

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {
    p0.dropTable(Favorit.TABLE_FAVORIT,true)

    p0.dropTable(FavoritTeam.TABLE_FAVORIT_TEAM,true)
    }
}

val Context.database :MyDatabaseOpenHelper
get() = MyDatabaseOpenHelper.getInstance(applicationContext)