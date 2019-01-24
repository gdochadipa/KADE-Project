package co.ocha.eplmatch.Searching

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.*
import co.ocha.eplmatch.Api.ApiRepository
import co.ocha.eplmatch.ClubTeam.TeamAdapter
import co.ocha.eplmatch.ClubTeam.TeamPresenter
import co.ocha.eplmatch.ClubTeam.TeamView
import co.ocha.eplmatch.DetailClub.DetailClubActivity
import co.ocha.eplmatch.Model.League.LeaguesItem
import co.ocha.eplmatch.Model.Team.TeamsItem
import co.ocha.eplmatch.R
import co.ocha.eplmatch.Untils.invisible
import co.ocha.eplmatch.Untils.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class SearchingTeamActivity : AppCompatActivity(),TeamView, AnkoComponent<Context> {


    private var teams: MutableList<TeamsItem?>? = mutableListOf()
    private lateinit var presenter: TeamPresenter
    private lateinit var adapter: TeamAdapter
    private lateinit var namaLiga: String
    private lateinit var mylist: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var spinner: Spinner
    private lateinit var searchView: SearchView
    private lateinit var text_error:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_searching_team)

        setContentView(createView(AnkoContext.Companion.create(ctx)))


        adapter = TeamAdapter(teams){
            if (it != null) {
                startActivity<DetailClubActivity>("idTeam" to "${it.idTeam}")
            }
            Toast.makeText(this,"${it?.strTeam}", Toast.LENGTH_SHORT).show()
        }
        mylist.adapter= adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamPresenter(this,gson,request)


        swipeRefresh.onRefresh {
            searchQuery(searchView)
        }
        searchQuery(searchView)
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamList(data: List<TeamsItem?>?) {
        swipeRefresh.isRefreshing= false
        teams!!.clear()
        if (data != null) {
            text_error.invisible()
            teams!!.addAll(data)
        }else{
         text_error.visible()
        }
        adapter.notifyDataSetChanged()
    }

    override fun showLeague(league: List<LeaguesItem>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun searchQuery(searchView: SearchView){
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(query: String?): Boolean {
                if (query.isNullOrEmpty()){
                    Toast.makeText(applicationContext,"Isikan data",Toast.LENGTH_SHORT).show()
                    return false
                }else{
                    presenter.getSearchTeam(query)
                    return true
                }
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query.isNullOrEmpty()){
                    Toast.makeText(applicationContext,"Isikan data",Toast.LENGTH_SHORT).show()
                    return false
                }else{
                    Toast.makeText(applicationContext,query,Toast.LENGTH_SHORT).show()
                    presenter.getSearchTeam(query)
                    return true
                }
            }
        })

        searchView.setOnCloseListener(object :SearchView.OnCloseListener{
            override fun onClose(): Boolean {
                text_error.visible()
                return true
            }
        })
    }


    override fun createView(ui: AnkoContext<Context>): View=with(ui) {
        linearLayout(){
            lparams(width= matchParent,height = matchParent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)
            cardView {

                searchView = searchView {
                    id=R.id.search_team
                }.lparams()
            }.lparams(width= matchParent,height = wrapContent)


            swipeRefresh = swipeRefreshLayout{
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)
                relativeLayout(){
                    lparams(width= matchParent,height = wrapContent)

                    mylist = recyclerView(){
                        id = R.id.mylist

                        layoutManager = LinearLayoutManager(ctx)

                    }.lparams(width= matchParent,height = wrapContent)

                    progressBar= progressBar {  }.lparams{centerHorizontally()}

                    text_error= textView("Data is not Found"){
                        visibility = View.INVISIBLE
                    }.lparams{centerInParent()}
                }

            }

        }
    }
}
