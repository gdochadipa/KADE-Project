package co.ocha.eplmatch.Fragment


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.*
import android.widget.Toast
import co.ocha.eplmatch.DetailClub.DetailClubActivity
import co.ocha.eplmatch.MatchActivity
import co.ocha.eplmatch.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



/**
 * A simple [Fragment] subclass.
 *
 */


class MatchFragment : Fragment(){

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var searchItem: MenuItem

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_match2, container, false)
        tabLayout = view.findViewById(R.id.tab_layout)
        viewPager = view.findViewById(R.id.view_pager)


        tabLayout.addTab(tabLayout.newTab().setText("Last Match"))
        tabLayout.addTab(tabLayout.newTab().setText("Next Match"))

        val adapter = TabPageAdapter(fragmentManager,savedInstanceState)
        viewPager.adapter = adapter

        tabLayout.setupWithViewPager(viewPager)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }


    inner class TabPageAdapter(fragmentManager: FragmentManager?, savedInstanceState: Bundle?) : FragmentPagerAdapter(fragmentManager){
        override fun getCount(): Int {
            return 2
        }

        override fun getItem(position: Int):Fragment? = when (position){
            0-> LastMatchFragment.newInstance()
            1-> NextFragment.newInstance()

            else->null
        }

        override fun getPageTitle(position: Int): CharSequence= when (position) {
            0 -> "Last Match"
            1 -> "Next Match"

            else->""
        }
    }



}


