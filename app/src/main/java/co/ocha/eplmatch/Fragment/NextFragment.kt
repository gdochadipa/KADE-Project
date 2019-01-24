package co.ocha.eplmatch.Fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import co.ocha.eplmatch.Api.ApiRepository
import co.ocha.eplmatch.DetailMatch
import co.ocha.eplmatch.Model.League.LeaguesItem
import co.ocha.eplmatch.Model.NextMatch.NextEventsItem
import co.ocha.eplmatch.Model.Search.EventItem
import co.ocha.eplmatch.NextEvent.ListLeague
import co.ocha.eplmatch.NextEvent.NextEventAdapter
import co.ocha.eplmatch.NextEvent.NextEventPresenter
import co.ocha.eplmatch.NextEvent.NextEventView

import co.ocha.eplmatch.R
import co.ocha.eplmatch.Untils.invisible
import co.ocha.eplmatch.Untils.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class NextFragment : Fragment(),NextEventView, AnkoComponent<Context> {


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private var nexts: MutableList<NextEventsItem?>? = mutableListOf()
    private var leagues: MutableList<LeaguesItem> = mutableListOf()
    private lateinit var adapter: NextEventAdapter
    private lateinit var presenter: NextEventPresenter
    private lateinit var spinner: Spinner
    private lateinit var namaLiga: String
    private  var listSpinner1: MutableList<String> = mutableListOf()
    private var listIdLeague1: MutableList<String> = mutableListOf()
    private lateinit var spinnerAdapter: SpinnerAdapter
    private lateinit var idLiga:String



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = NextEventAdapter(nexts){
            ctx.startActivity<DetailMatch>("idEvent" to "${it.idEvent}",
                    "idHomeTeam" to "${it.idHomeTeam}",
                    "idAwayTeam" to "${it.idAwayTeam}")
        }
        listTeam.adapter = adapter

        namaLiga = ""


        val request = ApiRepository()
        val gson = Gson()
        val list = ListLeague()
        presenter= NextEventPresenter(gson,request,this)



        val listL = list.listL()
        val listIdL = list.listIdL()

        spinnerAdapter = ArrayAdapter(ctx,android.R.layout.simple_spinner_dropdown_item,listL)
        spinner.adapter = spinnerAdapter


        spinner.onItemSelectedListener =  object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
               namaLiga = spinner.selectedItem.toString()
                idLiga = listIdL.get(p2).toString()
                presenter.nextEventList(idLiga)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
               presenter.nextEventList(idLiga)
            }
        }


        if(namaLiga.equals("")){
            Toast.makeText(context,"mohon di tunggu",Toast.LENGTH_SHORT).show()

        }else{
            swipeRefresh.onRefresh {
                presenter.nextEventList(idLiga)
            }
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


            swipeRefresh = swipeRefreshLayout{
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_green_dark,
                        android.R.color.holo_green_light)

                linearLayout {
                    lparams(width = matchParent, height = matchParent)
                    orientation = LinearLayout.VERTICAL



                    spinner = spinner {
                        id = R.id.spinner_next

                    }.lparams(width= matchParent,height = wrapContent){
                        margin = dip(5)
                    }

                    relativeLayout() {
                        lparams(width = matchParent, height = wrapContent)

                        listTeam = recyclerView() {
                            id = R.id.list_next
                            layoutManager = LinearLayoutManager(ctx)

                        }.lparams(width = matchParent, height = wrapContent)

                        progressBar = progressBar { }.lparams { centerHorizontally() }
                    }

                }
            }

        }
    }


    override fun showLoading() {
        progressBar.visible()    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showMatchList(data: List<NextEventsItem?>?) {

        swipeRefresh.isRefreshing = false
        nexts!!.clear()
        if (data != null) {
            nexts!!.addAll(data)
        }
        adapter.notifyDataSetChanged()
    }



    override fun showLeague(league: List<LeaguesItem>) {
        leagues.clear()
        leagues.addAll(league)
        val lenght = leagues.size.toInt()
        for (i in 0..leagues.size-1){
           listSpinner1.add(leagues[i].strLeague.toString())
            listIdLeague1.add(leagues[i].idLeague.toString())
        }

    }





    override fun showEventList(data2: List<EventItem?>?) {

    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                NextFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }

        fun newInstance(): NextFragment = NextFragment()
    }

}
