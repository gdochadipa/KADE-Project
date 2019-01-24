package co.ocha.eplmatch.Favorit.FavoritTeam


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.ocha.eplmatch.Db.FavoritTeamDB.FavoritTeam
import co.ocha.eplmatch.Db.database
import co.ocha.eplmatch.DetailClub.DetailClubActivity

import co.ocha.eplmatch.R
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



class FavoritTeamFragment : Fragment(),AnkoComponent<Context> {
    private lateinit var swipeRefresh:SwipeRefreshLayout
    private lateinit var listTeam:RecyclerView
    private var favorites: MutableList<FavoritTeam> = mutableListOf()
    private lateinit var adapter: FavoritTeamAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = FavoritTeamAdapter(favorites){
            ctx.startActivity<DetailClubActivity>("idTeam" to "${it.idTeam}")

        }
        listTeam.adapter = adapter
        showFavorit()
        swipeRefresh.onRefresh {
            favorites.clear()
            showFavorit()
        }
    }

    private fun showFavorit(){
        context?.database?.use {
            swipeRefresh.isRefreshing=false
            val result = select(FavoritTeam.TABLE_FAVORIT_TEAM)
            val favorit = result.parseList(classParser<FavoritTeam>())
            favorites.addAll(favorit)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return createView(AnkoContext.Companion.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui){
            linearLayout {
                lparams (width = matchParent, height = wrapContent)
                topPadding = dip(16)
                leftPadding = dip(16)
                rightPadding = dip(16)

                swipeRefresh = swipeRefreshLayout {
                    id=R.id.swipe
                    setColorSchemeResources(R.color.colorPrimary,
                            android.R.color.holo_blue_bright,
                            android.R.color.holo_blue_dark,
                            android.R.color.holo_blue_light)

                    listTeam = recyclerView {
                        id= R.id.list_team_favorit
                        lparams (width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }
                }
            }
        }
    }
    companion object {
        fun newInstance(): FavoritTeamFragment = FavoritTeamFragment()
    }

}
