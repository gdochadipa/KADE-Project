package co.ocha.eplmatch.ClubTeam


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import co.ocha.eplmatch.Api.ApiRepository
import co.ocha.eplmatch.DetailClub.DetailClubActivity
import co.ocha.eplmatch.Model.League.LeaguesItem
import co.ocha.eplmatch.Model.Team.TeamsItem
import co.ocha.eplmatch.NextEvent.ListLeague

import co.ocha.eplmatch.R
import co.ocha.eplmatch.R.id.listTeam
import co.ocha.eplmatch.Untils.invisible
import co.ocha.eplmatch.Untils.visible
import com.google.gson.Gson
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private var teams: MutableList<TeamsItem?>? = mutableListOf()
private var leagues: MutableList<LeaguesItem> = mutableListOf()
private lateinit var presenter: TeamPresenter
private lateinit var adapter: TeamAdapter
private lateinit var namaLiga: String
private lateinit var mylist: RecyclerView
private lateinit var progressBar: ProgressBar
private lateinit var swipeRefresh: SwipeRefreshLayout
private lateinit var spinner: Spinner
private lateinit var searchView: SearchView
private  var listSpinner: MutableList<String> = mutableListOf()
private var listIdLeague: MutableList<String> = mutableListOf()

/**
 * A simple [Fragment] subclass.
 *
 */
class TeamFragment : Fragment(),AnkoComponent<Context>,TeamView{



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        namaLiga = ""

        adapter = TeamAdapter(teams){
            if (it != null) {
                ctx.startActivity<DetailClubActivity>("idTeam" to "${it.idTeam}")
            }
            Toast.makeText(context,"${it?.strTeam}",Toast.LENGTH_SHORT).show()
        }
        mylist.adapter= adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter= TeamPresenter(this,gson,request)

        val listLeague = ListLeague()

        val listL = listLeague.listL()
        val listIdL = listLeague.listIdL()

        presenter.getLeaguelist()

        val spinnerItem = resources.getStringArray(R.array.leagueArray)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, listL)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
               namaLiga = spinner.selectedItem.toString()
                val a: String = listIdL.get(p2).toString()
                Toast.makeText(context,a,Toast.LENGTH_SHORT).show()

                presenter.getTeamList(namaLiga)

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

        if(namaLiga.equals("")){
            Toast.makeText(context,"mohon di tunggu",Toast.LENGTH_SHORT).show()
        }else{
            swipeRefresh.onRefresh {
                presenter.getTeamList(namaLiga)
            }
        }

            searchQuery(searchView)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return createView(AnkoContext.Companion.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View=with(ui) {
        linearLayout(){
            lparams(width= matchParent,height = matchParent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            searchView = searchView {
                id=R.id.search_team_f
            }.lparams(width= matchParent,height = wrapContent)

            spinner = spinner{
                id = R.id.spinner
            }
            swipeRefresh = swipeRefreshLayout{
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)
                relativeLayout(){
                    lparams(width= matchParent,height = wrapContent)

                    mylist = recyclerView(){
                        id = R.id.mylist_team

                        layoutManager = LinearLayoutManager(ctx)

                    }.lparams(width= matchParent,height = wrapContent)

                    progressBar= progressBar {  }.lparams{centerHorizontally()}
                }

            }

        }
    }

    override fun showTeamList(data: List<TeamsItem?>?) {
        swipeRefresh.isRefreshing= false
        teams!!.clear()
        if (data != null) {
            teams!!.addAll(data)
        }else{

        }
        adapter.notifyDataSetChanged()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showLoading() {
        progressBar.visible()
    }
    override fun showLeague(league: List<LeaguesItem>) {
        val datatL = league
        leagues.clear()
        leagues.addAll(datatL)
        for (i in 0..leagues.size){
           listSpinner.add(leagues[i].strLeague.toString())
            listIdLeague.add(leagues[i].idLeague.toString())
        }

    }

    private fun searchQuery(searchView: SearchView){

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(query: String?): Boolean {
                if (query.isNullOrEmpty()){
                    return false
                }else{
                    presenter.getSearchTeam(query)
                    return true
                }
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query.isNullOrEmpty()){
                    //Toast.makeText(context,"Isikan data",Toast.LENGTH_SHORT).show()
                    return false
                }else{
                    Toast.makeText(context,query,Toast.LENGTH_SHORT).show()
                    presenter.getSearchTeam(query)
                    return true
                }
            }
        })

        searchView.setOnCloseListener(object :SearchView.OnCloseListener{
            override fun onClose(): Boolean {
                presenter.getTeamList(namaLiga)
                return true
            }
        })
        searchView.setOnClickListener {
            Toast.makeText(context,"search",Toast.LENGTH_SHORT).show()
        }
    }


}
