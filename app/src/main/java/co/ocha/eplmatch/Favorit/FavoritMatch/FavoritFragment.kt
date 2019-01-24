package co.ocha.eplmatch.Favorit.FavoritMatch

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.ocha.eplmatch.Db.Favorit
import co.ocha.eplmatch.Db.database
import co.ocha.eplmatch.DetailMatch
import co.ocha.eplmatch.R
import co.ocha.eplmatch.R.color.*

import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FavoritFragment : Fragment(), AnkoComponent<Context> {
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var favorites: MutableList<Favorit> = mutableListOf()
    private lateinit var adapter: FavoritAdapter
    private lateinit var listEvent: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = FavoritAdapter(favorites) {
            ctx.startActivity<DetailMatch>("idEvent" to "${it.eventId}",
                    "idHomeTeam" to "${it.idHomeTeam}",
                    "idAwayTeam" to "${it.idAwayTeam}")
        }

        listEvent.adapter = adapter
        ShowFavorit()
        swipeRefresh.onRefresh {
            favorites.clear()
            ShowFavorit()
        }
    }

    private fun ShowFavorit(){
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(Favorit.TABLE_FAVORIT)
            val favorite = result.parseList(classParser<Favorit>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.Companion.create(ctx))
    }


    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) {
            linearLayout {
                lparams (width = matchParent, height = wrapContent)
                topPadding = dip(16)
                leftPadding = dip(16)
                rightPadding = dip(16)

                swipeRefresh = swipeRefreshLayout {
                    id=R.id.swipe
                    setColorSchemeResources(colorPrimary,
                            android.R.color.holo_blue_bright,
                            android.R.color.holo_blue_dark,
                            android.R.color.holo_blue_light)

                    listEvent = recyclerView {
                        id= R.id.list_event_favorit
                        lparams (width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }
                }
            }
        }
    }

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

        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                FavoritFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
