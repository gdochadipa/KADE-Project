package co.ocha.eplmatch.Favorit


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import co.ocha.eplmatch.Favorit.FavoritMatch.FavoritFragment
import co.ocha.eplmatch.Favorit.FavoritTeam.FavoritTeamFragment

import co.ocha.eplmatch.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class TabFavFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tab_fav, container, false)

        tabLayout = view.findViewById(R.id.tab_layout_liga)
        viewPager =  view.findViewById(R.id.view_pager_liga)

        tabLayout.addTab(tabLayout.newTab().setText("Favorit Match"))
        tabLayout.addTab(tabLayout.newTab().setText("Favorit Team"))



        val adapter = TabPageAdapter(fragmentManager,savedInstanceState)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.setupWithViewPager(viewPager)

        return view
    }

    inner class TabPageAdapter(fragmentManager: FragmentManager?,savedInstanceState: Bundle?) : FragmentPagerAdapter(fragmentManager){
        override fun getCount(): Int {
            return 2
        }

        override fun getItem(position: Int):Fragment? = when (position){
            0-> FavoritFragment.newInstance("","")
            1-> FavoritTeamFragment.newInstance()


            else->null
        }

        override fun getPageTitle(position: Int): CharSequence= when (position) {
            0 -> "Favorit Match"
            1 -> "Favorit Team"

            else->""
        }


    }

    companion object {
        fun newInstance(): TabFavFragment = TabFavFragment()
    }


}


