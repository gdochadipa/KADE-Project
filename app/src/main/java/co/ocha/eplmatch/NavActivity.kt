package co.ocha.eplmatch

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import co.ocha.eplmatch.ClubTeam.TeamFragment
import co.ocha.eplmatch.Favorit.FavoritTeam.FavoritTeamFragment
import co.ocha.eplmatch.Favorit.TabFavFragment
import co.ocha.eplmatch.Fragment.MatchFragment
import co.ocha.eplmatch.Searching.SearchingMatchActivity
import co.ocha.eplmatch.Searching.SearchingTeamActivity
import kotlinx.android.synthetic.main.activity_nav.*
import org.jetbrains.anko.searchManager
import org.jetbrains.anko.startActivity

class NavActivity : AppCompatActivity() {

private var test:String=""
private lateinit var searchItem: MenuItem


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav)


         navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    loadLastMatchFragment(savedInstanceState)
                    test ="match"

                }
                R.id.navigation_dashboard -> {
                    loadNextMatchFragment(savedInstanceState)
                    test="team"

                }

                R.id.navigation_favorit -> {
                    //loadFavorit(savedInstanceState)
                   loadFavorit(savedInstanceState)
                    test="favorit"

                }

            }
            true
        }
        navigation.selectedItemId = R.id.navigation_home



//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }



    private fun loadLastMatchFragment(savedInstanceState: Bundle?){
        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container,
                            MatchFragment(),
                            MatchFragment::class.java.simpleName).commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_menu_search,menu)
         searchItem = menu!!.findItem(R.id.search_menu)




        searchItem.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener{
            override fun onMenuItemClick(p0: MenuItem?): Boolean {
                if (test.equals("match")){
                    startActivity<SearchingMatchActivity>()
                }else if (test.equals("team")){
                    startActivity<SearchingTeamActivity>()
                }
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }



    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.search_menu ->{
//                Toast.makeText(this,"${test}",Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadNextMatchFragment(savedInstanceState: Bundle?){
        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container,TeamFragment(),
                            TeamFragment::class.java.simpleName).commit()
        }
    }

    private fun loadFavorit(savedInstanceState: Bundle?){
        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, TabFavFragment(),
                            TabFavFragment::class.java.simpleName).commit()
        }
    }



}
