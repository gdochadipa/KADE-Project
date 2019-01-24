package co.ocha.eplmatch

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import co.ocha.eplmatch.Api.ApiRepository
import co.ocha.eplmatch.Api.Detail
import co.ocha.eplmatch.Api.ImageClubView
import co.ocha.eplmatch.Db.Favorit
import co.ocha.eplmatch.Db.database
import co.ocha.eplmatch.Model.DetEventsItem
import co.ocha.eplmatch.Model.Image
import co.ocha.eplmatch.R.color.colorAccent
import co.ocha.eplmatch.R.menu.detail_menu
import com.bumptech.glide.Glide
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.design.snackbar
import co.ocha.eplmatch.R.drawable.ic_add_to_favorites
import co.ocha.eplmatch.R.drawable.ic_added_to_favorites
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class DetailMatch : AppCompatActivity(),ImageClubView{

    private lateinit var findImage: Detail
    private lateinit var image1: ImageView
    private lateinit var imageHome: ImageView

    private lateinit var strHomeTeam: TextView
    private lateinit var strAwayTeam: TextView
    private lateinit var intAwayScore: TextView
    private lateinit var intHomeScore: TextView

    private lateinit var strHomeGoalDetails:TextView
    private lateinit var strAwayGoalDetails:TextView

    private lateinit var intAwayShots:TextView
    private lateinit var intHomeShots:TextView

    private lateinit var strHomeFormation:TextView
    private lateinit var strAwayFormation:TextView


    private lateinit var strHomeLineupGoalkeeper:TextView
    private lateinit var strHomeLineupDefense:TextView
    private lateinit var strHomeLineupMidfield:TextView
    private lateinit var strHomeLineupForward:TextView
    private lateinit var strHomeLineupSubstitutes:TextView

    private lateinit var strAwayLineupGoalkeeper:TextView
    private lateinit var strAwayLineupDefense:TextView
    private lateinit var strAwayLineupMidfield:TextView
    private lateinit var strAwayLineupForward:TextView
    private lateinit var strAwayLineupSubstitutes:TextView

    private lateinit var fav: Array<String?>
    private var homeId: String = ""
    private var awayId: String = ""
    private var evID: String = ""
    private var strDateMatch: String = ""
    private var strTime: String = ""
    private var dateEvent:String =""
    private var strEve: String = ""
    private lateinit var eventID: String
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var linearLayout: LinearLayout
    private var homeID: String = ""
    private var awayID: String = ""
    private var home_scr: String = ""
    private var away_scr: String = ""

    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        homeId = intent.getStringExtra("idHomeTeam")
        awayId = intent.getStringExtra("idAwayTeam")
        eventID = intent.getStringExtra("idEvent")

        swipeRefresh = swipeRefreshLayout {
            setColorSchemeResources(colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light)
        linearLayout=linearLayout() {
            orientation = LinearLayout.VERTICAL

            scrollView() {
                lparams(height = matchParent, width = matchParent)
                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    weightSum = 2f

                    linearLayout {
                        orientation = LinearLayout.VERTICAL

                        strHomeTeam = textView() {
                            gravity = Gravity.CENTER
                            topPadding = dip(15)
                            textSize = 15f
                        }.lparams(width = matchParent, height = wrapContent) {
                            gravity = Gravity.CENTER
                        }
                        intHomeScore = textView() {
                            gravity = Gravity.CENTER
                            topPadding = dip(15)
                            textSize = 15f
                        }.lparams(width = matchParent, height = wrapContent) {
                            gravity = Gravity.CENTER
                        }

                        imageHome = imageView {

                            topPadding = dip(15)
                        }.lparams(width = dip(60), height = dip(60)) {
                            gravity = Gravity.CENTER
                        }

                        strHomeGoalDetails = textView() {

                            topPadding = dip(15)
                            textSize = 15f
                        }.lparams(width = matchParent, height = wrapContent) {
                            gravity = Gravity.CENTER
                        }

                        strHomeFormation = textView() {

                            topPadding = dip(15)
                            textSize = 15f
                        }.lparams(width = matchParent, height = wrapContent) {
                            gravity = Gravity.CENTER
                        }

                        strHomeLineupGoalkeeper = textView() {

                            topPadding = dip(15)
                            textSize = 15f
                        }.lparams(width = matchParent, height = wrapContent) {
                            gravity = Gravity.CENTER
                        }

                        strHomeLineupDefense = textView() {

                            topPadding = dip(15)
                            textSize = 15f
                        }.lparams(width = matchParent, height = wrapContent) {
                            gravity = Gravity.CENTER
                        }

                        strHomeLineupMidfield = textView() {

                            topPadding = dip(15)
                            textSize = 15f
                        }.lparams(width = matchParent, height = wrapContent) {
                            gravity = Gravity.CENTER
                        }

                        strHomeLineupForward = textView() {

                            topPadding = dip(15)
                            textSize = 15f
                        }.lparams(width = matchParent, height = wrapContent) {
                            gravity = Gravity.CENTER
                        }

                        strHomeLineupSubstitutes = textView() {

                            topPadding = dip(15)
                            textSize = 15f
                        }.lparams(width = matchParent, height = wrapContent) {
                            gravity = Gravity.CENTER
                        }


                    }.lparams(width = wrapContent, height = wrapContent) {
                        weight = 0.8f
                    }

                    //tengah
                    linearLayout {
                        textView("VS") {
                            topPadding = dip(50)
                        }.lparams(width = matchParent, height = wrapContent) {
                            gravity = Gravity.CENTER
                        }

                    }.lparams(width = wrapContent, height = wrapContent) {
                        weight = 0.4f
                    }

                    //kanan
                    linearLayout {
                        orientation = LinearLayout.VERTICAL

                        strAwayTeam = textView() {
                            gravity = Gravity.CENTER
                            topPadding = dip(15)
                            textSize = 15f
                        }.lparams(width = matchParent, height = wrapContent) {
                            gravity = Gravity.CENTER
                        }
                        intAwayScore = textView() {
                            gravity = Gravity.CENTER
                            topPadding = dip(15)
                            textSize = 15f
                        }.lparams(width = matchParent, height = wrapContent) {
                            gravity = Gravity.CENTER
                        }
                        image1 = imageView {

                            topPadding = dip(15)
                        }.lparams(width = dip(60), height = dip(60)) {
                            gravity = Gravity.CENTER
                        }

                        strAwayGoalDetails = textView() {

                            topPadding = dip(15)
                            textSize = 15f
                        }.lparams(width = matchParent, height = wrapContent) {
                            gravity = Gravity.CENTER
                        }

                        strAwayFormation = textView() {

                            topPadding = dip(15)
                            textSize = 15f
                        }.lparams(width = matchParent, height = wrapContent) {
                            gravity = Gravity.CENTER
                        }

                        strAwayLineupGoalkeeper = textView() {

                            topPadding = dip(15)
                            textSize = 15f
                        }.lparams(width = matchParent, height = wrapContent) {
                            gravity = Gravity.CENTER
                        }

                        strAwayLineupDefense = textView() {

                            topPadding = dip(15)
                            textSize = 15f
                        }.lparams(width = matchParent, height = wrapContent) {
                            gravity = Gravity.CENTER
                        }

                        strAwayLineupMidfield = textView() {

                            topPadding = dip(15)
                            textSize = 15f
                        }.lparams(width = matchParent, height = wrapContent) {
                            gravity = Gravity.CENTER
                        }

                        strAwayLineupForward = textView() {

                            topPadding = dip(15)
                            textSize = 15f
                        }.lparams(width = matchParent, height = wrapContent) {
                            gravity = Gravity.CENTER
                        }

                        strAwayLineupSubstitutes = textView() {

                            topPadding = dip(15)
                            textSize = 15f
                        }.lparams(width = matchParent, height = wrapContent) {
                            gravity = Gravity.CENTER
                        }

                    }.lparams(width = wrapContent, height = wrapContent) {
                        weight = 0.8f
                    }
                }.lparams(width = matchParent, height = wrapContent)
            }.lparams(width = matchParent, height = matchParent)
        }
    }

        val apiRepository = ApiRepository()
        val gson = Gson()
        findImage = Detail(apiRepository,gson,this)
        findImage.findLogo(awayId,homeId)
        findImage.findDetailMatch(eventID)
        favoritState()
        swipeRefresh.onRefresh {
            findImage.findDetailMatch(eventID)
        }
    }

    override fun showImage(data1: List<Image>, data2: List<Image>) {
        Glide.with(this).load(data1[0].teamBandage).into(image1)
        Glide.with(this).load(data2[0].teamBandage).into(imageHome)
    }
    private lateinit var detEventsItem: DetEventsItem
    override fun showDetail(data: List<DetEventsItem>) {
        swipeRefresh.isRefreshing = false
        val matches = data[0]


        evID = matches.idEvent.toString()
        strDateMatch = matches.strDate.toString()
        strEve = matches.strEvent.toString()
        homeID =matches.idHomeTeam.toString()
        awayID =matches.idAwayTeam.toString()
        home_scr=matches.intHomeScore.toString()
        away_scr=matches.intAwayScore.toString()
        strTime=matches.strTime.toString()
        dateEvent=matches.dateEvent.toString()

        strHomeTeam.text = matches.strHomeTeam.toString()
        strAwayTeam.text = matches.strAwayTeam.toString()

        intHomeScore.text = matches.intHomeScore.toString()
        intAwayScore.text = matches.intAwayScore.toString()

        strAwayGoalDetails.text = matches.strAwayGoalDetails.toString().replace(";","\n")
        strHomeGoalDetails.text = matches.strHomeGoalDetails.toString().replace(";","\n")

        strHomeFormation.text = matches.strHomeFormation.toString()
        strAwayFormation.text = matches.strAwayFormation.toString()

        strHomeLineupDefense.text = matches.strHomeLineupDefense.toString().replace(";","\n")
        strHomeLineupGoalkeeper.text = matches.strHomeLineupGoalkeeper.toString().replace(";","\n")
        strHomeLineupMidfield.text = matches.strHomeLineupMidfield.toString().replace(";","\n")
        strHomeLineupForward.text = matches.strHomeLineupForward.toString().replace(";","\n")
        strHomeLineupSubstitutes.text = matches.strHomeLineupSubstitutes.toString().replace(";","\n")

        strAwayLineupDefense.text = matches.strAwayLineupDefense.toString().replace(";","\n")
        strAwayLineupGoalkeeper.text = matches.strAwayLineupGoalkeeper.toString().replace(";","\n")
        strAwayLineupMidfield.text = matches.strAwayLineupMidfield.toString().replace(";","\n")
        strAwayLineupForward.text = matches.strAwayLineupForward.toString().replace(";","\n")
        strAwayLineupSubstitutes.text = matches.strAwayLineupSubstitutes.toString().replace(";","\n")

        strHomeTeam.text = matches.strHomeTeam.toString()
        strAwayTeam.text = matches.strAwayTeam.toString()


    }

    private fun favoritState(){
        database.use {
            val result = select(Favorit.TABLE_FAVORIT)
                    .whereArgs("(EVENT_ID = {id})","id" to eventID)
            val favorit = result.parseList(classParser<Favorit>())
            if (!favorit.isEmpty()) isFavorite = true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu,menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home ->{
                finish()
                true
            }
            R.id.add_to_favorite ->{
                if(isFavorite) {
                    removefromFavorite()
                }else{
                    addToFavorite()
                }


                isFavorite= !isFavorite
                setFavorite()
                true
            }
            else ->super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite(){
        try {
            database.use {
                insert(Favorit.TABLE_FAVORIT,
                        Favorit.EVENT_ID to evID,
                        Favorit.STR_EVENT to strEve,
                        Favorit.HOME_TEAM_ID to homeID,
                        Favorit.AWAY_TEAM_ID to awayID,
                        Favorit.HOME_TEAM_SCR to home_scr,
                        Favorit.AWAY_TEAM_SCR to away_scr,
                        Favorit.STR_TIME to strTime,
                        Favorit.DATE_EVENT to dateEvent)
            }
            snackbar(swipeRefresh, "Added to favorite").show()
        } catch (e: SQLiteConstraintException){
            snackbar(swipeRefresh, e.localizedMessage).show()
        }

    }

    private fun removefromFavorite(){
        try {
            database.use {
                delete(Favorit.TABLE_FAVORIT, "EVENT_ID = {id}","id" to eventID)
            }
            snackbar(swipeRefresh,"Removed to favorite").show()
        }catch (e: SQLiteConstraintException){
            snackbar(swipeRefresh,e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }

}
