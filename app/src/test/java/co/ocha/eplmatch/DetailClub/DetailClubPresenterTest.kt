package co.ocha.eplmatch.DetailClub

import co.ocha.eplmatch.Api.ApiRepository
import co.ocha.eplmatch.Api.MatchDBApi
import co.ocha.eplmatch.Model.Player.PlayerResponse
import co.ocha.eplmatch.Model.Team.TeamResponse
import co.ocha.eplmatch.Model.Team.TeamsItem
import co.ocha.eplmatch.Untils.TestContextProvider
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailClubPresenterTest {


    @Mock
    private
    lateinit var view: ClubView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: DetailClubPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter= DetailClubPresenter(apiRepository,gson,view,TestContextProvider())
    }

    @Test
    fun findDetailMatch() {
        val team: MutableList<TeamsItem> = mutableListOf()
        val response = TeamResponse(team)
        val idTeam = "133604"

        Mockito.`when`(gson.fromJson(apiRepository.doRequest(MatchDBApi.getLookupTeam(idTeam)),TeamResponse::class.java))
                .thenReturn(response)

        presenter.findDetailMatch(idTeam)

        if(!response.equals(null)){
            Mockito.verify(view).showDetail(team)
        }
    }
}