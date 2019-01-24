package co.ocha.eplmatch.Db.FavoritTeamDB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import co.ocha.eplmatch.Db.MyDatabaseOpenHelper
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper
import org.jetbrains.anko.db.createTable

class MyDBOpenHelperTeamFavorit(ctx: Context): ManagedSQLiteOpenHelper(ctx,"FavoritTeam.db",null,1){
    companion object {
        private var instance: MyDBOpenHelperTeamFavorit? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDBOpenHelperTeamFavorit{
            if (instance==null){
                instance = MyDBOpenHelperTeamFavorit(ctx.applicationContext)
            }
            return instance as MyDBOpenHelperTeamFavorit
        }
    }

    override fun onCreate(p0: SQLiteDatabase?) {

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

val Context.database :MyDBOpenHelperTeamFavorit
get() = MyDBOpenHelperTeamFavorit.getInstance(applicationContext)