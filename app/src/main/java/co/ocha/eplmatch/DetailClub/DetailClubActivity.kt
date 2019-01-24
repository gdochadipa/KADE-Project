package co.ocha.eplmatch.DetailClub

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.support.design.widget.TabLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import co.ocha.eplmatch.R.menu.detail_menu
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import co.ocha.eplmatch.Api.ApiRepository
import co.ocha.eplmatch.ClubTeam.TeamAdapter
import co.ocha.eplmatch.ClubTeam.TeamPresenter
import co.ocha.eplmatch.ClubTeam.TeamView
import co.ocha.eplmatch.Db.FavoritTeamDB.FavoritTeam
import co.ocha.eplmatch.Db.database
import co.ocha.eplmatch.DetailClub.DetailPlayer.DetailPlayerActivity
import co.ocha.eplmatch.DetailClub.PlayerClub.ListPlayer
import co.ocha.eplmatch.DetailClub.PlayerClub.ListPlayerAdapter
import co.ocha.eplmatch.DetailClub.PlayerClub.ListPlayerPresenter
import co.ocha.eplmatch.Model.Detail.PlayersItem
import co.ocha.eplmatch.Model.Player.PlayerItem
import co.ocha.eplmatch.Model.Team.TeamsItem
import co.ocha.eplmatch.R.drawable.ic_add_to_favorites
import co.ocha.eplmatch.R.drawable.ic_added_to_favorites
import co.ocha.eplmatch.R
import co.ocha.eplmatch.Untils.invisible
import co.ocha.eplmatch.Untils.visible
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail_club.*
import kotlinx.android.synthetic.main.fragment_detail_club.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import java.sql.SQLDataException

private lateinit var idTeam: String
private var teams: MutableList<TeamsItem?>? = mutableListOf()
private lateinit var presenter: TeamPresenter
private lateinit var adapter: TeamAdapter
private lateinit var detailTeam: DetailClubPresenter
private var menuItem: Menu? = null
private var isFavorite: Boolean = false


private var team_name: String = ""
private var team_logo: String = ""



class DetailClubActivity : AppCompatActivity(), ClubView{



    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_club)

        val intent = intent
        idTeam = intent.getStringExtra("idTeam")

        setSupportActionBar(toolbar)

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))


        val apiRepository = ApiRepository()
        val gson = Gson()
        detailTeam = DetailClubPresenter(apiRepository,gson,this)
        detailTeam.findDetailMatch(idTeam)
        favoritCheck()
    }

    override fun showDetail(data: List<TeamsItem?>?) {
       val teams= data!![0]

        Glide.with(this).load(teams!!.strTeamBadge).into(teamBadge)
        strTeam.text= teams!!.strTeam.toString()
        yearTeam.text = teams!!.intFormedYear
        homeStadion.text = teams!!.strStadium
        team_name = teams!!.strTeam.toString()
        team_logo = teams!!.strTeamBadge.toString()
    }

    private fun favoritCheck(){
        database.use {
            val result = select(FavoritTeam.TABLE_FAVORIT_TEAM)
                    .whereArgs("(TEAM_ID = {id})","id" to idTeam)
            val FavoritTeam = result.parseList(classParser<FavoritTeam>())
            if(!FavoritTeam.isEmpty()){
                isFavorite = true
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorit()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home ->{
                finish()
                true

            }
            R.id.add_to_favorite->{
                if(isFavorite) {
                    removeFavorit()
                }else{
                    addFavorit()
                }
                isFavorite = !isFavorite
                setFavorit()
                true
            }

            else ->super.onOptionsItemSelected(item)
        }

    }

    private fun addFavorit(){
        try {
            database.use {
                insert(FavoritTeam.TABLE_FAVORIT_TEAM,
                        FavoritTeam.TEAM_ID to idTeam,
                        FavoritTeam.TEAM_NAME to team_name,
                        FavoritTeam.TEAM_LOGO to team_logo)
            }
           Toast.makeText(this,"Added to Favorit",Toast.LENGTH_SHORT).show()
        }catch (e: SQLiteConstraintException){
            Toast.makeText(this,e.localizedMessage,Toast.LENGTH_SHORT).show()
        }

    }

    private fun removeFavorit(){
        try {
            database.use {
                delete(FavoritTeam.TABLE_FAVORIT_TEAM,"TEAM_ID = {id}","id" to idTeam)
            }
            Toast.makeText(this,"deleted from Favorit",Toast.LENGTH_SHORT).show()
        }catch (e: SQLiteConstraintException){
            Toast.makeText(this,e.localizedMessage,Toast.LENGTH_SHORT).show()
        }
    }

    private fun setFavorit(){
        if(isFavorite){
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        }else{
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
        }
    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? = when(position){
            0-> OverviewFragment.newInstance()
            1-> PlayerFragment.newInstance()

            else->null
        }

        override fun getCount(): Int {

            return 2
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    class PlaceholderFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_detail_club, container, false)
            rootView.section_label.text = getString(R.string.section_format, arguments?.getInt(ARG_SECTION_NUMBER))
            return rootView
        }

        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private val ARG_SECTION_NUMBER = "section_number"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }

    class OverviewFragment : Fragment(),ClubView {

    private lateinit var overview_text: TextView
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            // Inflate the layout for this fragment

            val apiRepository = ApiRepository()
            val gson = Gson()
            detailTeam = DetailClubPresenter(apiRepository,gson,this)
            detailTeam.findDetailMatch(idTeam)

          val viewOverview=  inflater.inflate(R.layout.fragment_overview, container, false)
            overview_text = viewOverview.findViewById(R.id.overview_text)
            return viewOverview
        }
        override fun showDetail(data: List<TeamsItem?>?) {
            val teams= data!![0]
            overview_text.text = teams!!.strDescriptionEN.toString()
        }
        companion object {
            fun newInstance(): OverviewFragment = OverviewFragment()
        }

    }

    class PlayerFragment : Fragment(),ListPlayer,AnkoComponent<Context> {

        private lateinit var swipeRefreshLayout: SwipeRefreshLayout
        private lateinit var listPlayer: RecyclerView
        private lateinit var progressBar: ProgressBar
        private lateinit var adapter: ListPlayerAdapter
        private lateinit var presenterPlayer: ListPlayerPresenter
        private var players: MutableList<PlayerItem> = mutableListOf()

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {

            adapter = ListPlayerAdapter(players){
                ctx.startActivity<DetailPlayerActivity>("idPlayer" to "${it.idPlayer}")
            }
            return createView(AnkoContext.Companion.create(ctx))
        }

        override fun createView(ui: AnkoContext<Context>): View = with(ui){
            linearLayout(){
                lparams(width= matchParent,height = matchParent)
                orientation = LinearLayout.VERTICAL
                topPadding = dip(16)
                leftPadding = dip(16)
                rightPadding = dip(16)


                swipeRefreshLayout = swipeRefreshLayout{
                    setColorSchemeResources(R.color.colorAccent,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            android.R.color.holo_red_light)
                    relativeLayout(){
                        lparams(width= matchParent,height = wrapContent)

                        listPlayer = recyclerView(){
                            id = R.id.listPlayer

                            layoutManager = LinearLayoutManager(ctx)

                        }.lparams(width= matchParent,height = wrapContent)

                        progressBar= progressBar {  }.lparams{centerHorizontally()}
                    }

                }

            }
        }

        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
            listPlayer.adapter = adapter

            val apiRepository = ApiRepository()
            val gson = Gson()

            presenterPlayer= ListPlayerPresenter(apiRepository,gson,this)

            presenterPlayer.getPlayers(idTeam)

            swipeRefreshLayout.onRefresh {
                presenterPlayer.getPlayers(idTeam)
            }

        }

        override fun hideLoading() {
            progressBar.invisible()
        }

        override fun showListPlayer(data: List<PlayerItem>) {
           swipeRefreshLayout.isRefreshing = false
            players.clear()
            players.addAll(data)
            adapter.notifyDataSetChanged()
        }

        override fun showDetail(data: List<PlayersItem?>?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun showLoading() {
           progressBar.visible()
        }

        companion object {
            fun newInstance(): PlayerFragment = PlayerFragment()
        }
    }
}
