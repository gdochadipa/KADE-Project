package co.ocha.eplmatch.TabLayout

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import co.ocha.eplmatch.Fragment.LastMatchFragment
import co.ocha.eplmatch.Fragment.NextFragment

class PagerAdapter(fragmentManager: FragmentManager) :FragmentPagerAdapter(fragmentManager){
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int):Fragment? = when (position){
        0->LastMatchFragment.newInstance()
        1->NextFragment.newInstance()

        else->null
    }

    override fun getPageTitle(position: Int): CharSequence= when (position) {
        0 -> "Last Match"
        1 -> "Next Match"

        else->""
    }
}