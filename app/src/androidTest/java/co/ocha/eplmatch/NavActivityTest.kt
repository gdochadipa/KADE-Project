package co.ocha.eplmatch

import android.support.design.widget.TabLayout
import android.test.ActivityInstrumentationTestCase2
import android.support.test.espresso.Espresso.*
import android.support.test.espresso.PerformException
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import java.util.EnumSet.allOf
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.TableLayout
import co.ocha.eplmatch.Model.League.LeaguesItem
import co.ocha.eplmatch.NextEvent.ListLeague
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Matcher
import java.lang.Thread.sleep
import org.jetbrains.anko.Android
import org.junit.Assert.*

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavActivityTest{
    val Arsenal = "Arsenal"
    @Rule
    @JvmField var activityRule = ActivityTestRule(NavActivity::class.java)


    @Test
    fun LastMatchTest(){
//Last Match
        onView(withId(R.id.spinner_match)).perform(click())
        onView(withText("Spanish La Liga")).perform(click())
        sleep(2000)
        onView(withId(R.id.list_match)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(R.id.list_match)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(8, click()))
        sleep(2000)
        onView(withId(R.id.add_to_favorite))
                .check(matches(isDisplayed()))
        onView(withId(R.id.add_to_favorite)).perform(click())
        onView(withText("Added to favorite"))
                .check(matches(isDisplayed()))

//Next Ma



    }

    @Test
    fun NextTeamTest(){
        onView(withId(R.id.view_pager)).check(matches(isDisplayed()))
        onView(withId(R.id.view_pager)).perform(swipeLeft())
        onView(withId(R.id.list_next)).check(matches(isDisplayed()))
        sleep(2000)
        onView(withId(R.id.spinner_next)).perform(click())
        onView(withText("Spanish La Liga")).perform(click())
        onView(withId(R.id.list_next)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(7))
        onView(withId(R.id.list_next)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))
        sleep(2000)
        onView(withId(R.id.add_to_favorite))
                .check(matches(isDisplayed()))
        onView(withId(R.id.add_to_favorite)).perform(click())
        onView(withText("Added to favorite"))
                .check(matches(isDisplayed()))
    }

    @Test
    fun searchMatch(){
        onView(withId(R.id.search_menu)).perform(click())
        onView(withId(R.id.mylist_search_match)).check(matches(isDisplayed()))
        sleep(3000)
        onView(withId(R.id.mylist_search_match)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1))
        onView(withId(R.id.mylist_search_match)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        sleep(2000)
        onView(withId(R.id.add_to_favorite))
                .check(matches(isDisplayed()))
        onView(withId(R.id.add_to_favorite)).perform(click())
        onView(withText("Added to favorite"))
                .check(matches(isDisplayed()))

    }

    @Test
    fun TeamsTest(){
        onView(withId(R.id.navigation)).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_dashboard)).perform(click())
        onView(withId(R.id.search_team_f)).perform(click())
        onView(withId(R.id.spinner)).perform(click())
        onView(withText("English Premier League")).perform(click())
        sleep(5000)
        onView(withId(R.id.mylist_team)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1))
        onView(withId(R.id.mylist_team)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        sleep(2000)

        onView(withId(R.id.add_to_favorite))
                .check(matches(isDisplayed()))
        onView(withId(R.id.add_to_favorite)).perform(click())


        onView(withId(R.id.container)).check(matches(isDisplayed()))
        onView(withText("PLAYER")).perform(click())
        sleep(2000)

        onView(withId(R.id.listPlayer)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1))
        onView(withId(R.id.listPlayer)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))

        sleep(2000)
        android.support.test.espresso.Espresso.pressBack()
    }

    @Test
    fun FavoritTest(){
        onView(withId(R.id.navigation)).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_favorit)).perform(click())

        onView(withId(R.id.list_event_favorit)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(R.id.list_event_favorit)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        sleep(2000)

        onView(withId(R.id.add_to_favorite))
                .check(matches(isDisplayed()))
        onView(withId(R.id.add_to_favorite)).perform(click())

        android.support.test.espresso.Espresso.pressBack()

        onView(withId(R.id.view_pager_liga)).check(matches(isDisplayed()))
        onView(withId(R.id.view_pager_liga)).perform(swipeLeft())


        onView(withId(R.id.list_team_favorit)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        sleep(2000)

        onView(withId(R.id.list_team_favorit)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        sleep(2000)

        onView(withId(R.id.add_to_favorite))
                .check(matches(isDisplayed()))
        onView(withId(R.id.add_to_favorite)).perform(click())

        android.support.test.espresso.Espresso.pressBack()
    }

}