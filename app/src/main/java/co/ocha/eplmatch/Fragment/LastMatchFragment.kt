package co.ocha.eplmatch.Fragment


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
import co.ocha.eplmatch.DetailMatch
import co.ocha.eplmatch.LastEvent.MainAdapter
import co.ocha.eplmatch.LastEvent.LastEventPresenter
import co.ocha.eplmatch.LastEvent.LastEventView
import co.ocha.eplmatch.Model.LastMatch.LastEventsItem
import co.ocha.eplmatch.NextEvent.ListLeague

import co.ocha.eplmatch.R
import co.ocha.eplmatch.Untils.invisible
import co.ocha.eplmatch.Untils.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class LastMatchFragment : Fragment(), AnkoComponent<Context>,LastEventView {
    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var spinner: Spinner
    private var matches: MutableList<LastEventsItem> = mutableListOf()
    private lateinit var presenter: LastEventPresenter
    private lateinit var adapter: MainAdapter
    private var namaLiga = ""
    private lateinit var idLiga:String

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = MainAdapter(matches){
            ctx.startActivity<DetailMatch>("idEvent" to "${it.idEvent}",
                    "idHomeTeam" to "${it.idHomeTeam}",
                    "idAwayTeam" to "${it.idAwayTeam}")
        }
        listTeam.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()

        presenter = LastEventPresenter(this,request,gson)

        val listLeague = ListLeague()

        val listL = listLeague.listL()
        val listIdL = listLeague.listIdL()


        val spinnerAdapter =  ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item,listL)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                namaLiga = spinner.selectedItem.toString()
                idLiga = listIdL.get(p2).toString()
                presenter.getMatchList(idLiga)

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

        swipeRefresh.onRefresh {
            presenter.getMatchList(idLiga)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return createView(AnkoContext.Companion.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        linearLayout(){
            lparams(width= matchParent,height = matchParent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)


            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)


                linearLayout {
                    lparams(width = matchParent, height = matchParent)
                    orientation = LinearLayout.VERTICAL


                    spinner = spinner {
                        id = R.id.spinner_match

                    }.lparams(width = matchParent, height = wrapContent) {
                        margin = dip(5)
                    }
                    relativeLayout() {
                        lparams(width = matchParent, height = wrapContent)

                        listTeam = recyclerView() {
                            id = R.id.list_match
                            layoutManager = LinearLayoutManager(ctx)

                        }.lparams(width = matchParent, height = wrapContent)

                        progressBar = progressBar { }.lparams { centerHorizontally() }
                    }

                }
            }
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()    }

    override fun showMatchList(data: List<LastEventsItem>) {
        swipeRefresh.isRefreshing = false
        matches.clear()
        matches.addAll(data)
        adapter.notifyDataSetChanged()
    }
    companion object {
        fun newInstance(): LastMatchFragment = LastMatchFragment()
    }
}
