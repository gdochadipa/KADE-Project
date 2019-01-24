package co.ocha.eplmatch.DetailClub.PlayerClub

import co.ocha.eplmatch.Api.ApiRepository
import co.ocha.eplmatch.Api.MatchDBApi
import co.ocha.eplmatch.Model.Detail.PlayersItem
import co.ocha.eplmatch.Model.LastMatch.LastEventsItem
import co.ocha.eplmatch.Model.Player.PlayerItem
import co.ocha.eplmatch.Model.Player.PlayerResponse
import co.ocha.eplmatch.Untils.TestContextProvider
import com.google.gson.Gson
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ListPlayerPresenterTest {

    @Mock
    private
    lateinit var view: ListPlayer

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: ListPlayerPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = ListPlayerPresenter(apiRepository,gson,view,TestContextProvider())
    }

    @Test
    fun getPlayers() {
        val players: MutableList<PlayerItem> = mutableListOf()
        val response = PlayerResponse(players)
        val idTeam = "133604"
        val link="https://www.thesportsdb.com/api/v1/json/1/lookup_all_players.php?id=133604"

        Mockito.`when`(gson.fromJson(apiRepository.doRequest(link),PlayerResponse::class.java)).thenReturn(response)

        presenter.getPlayers(idTeam)

       if(!response.equals(null)){
           Mockito.verify(view).showListPlayer(players)
       }

    }
}