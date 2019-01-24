package co.ocha.eplmatch.DetailClub.DetailPlayer

import android.media.Image
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import co.ocha.eplmatch.R.id.toolbar
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import co.ocha.eplmatch.Api.ApiRepository
import co.ocha.eplmatch.DetailClub.PlayerClub.ListPlayer
import co.ocha.eplmatch.DetailClub.PlayerClub.ListPlayerPresenter
import co.ocha.eplmatch.Model.Detail.PlayersItem
import co.ocha.eplmatch.Model.Player.PlayerItem
import co.ocha.eplmatch.R
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_player.*
import org.jetbrains.anko.appcompat.v7.toolbar


private lateinit var presenter: ListPlayerPresenter
private var players: MutableList<PlayerItem> = mutableListOf()
private var idPlayer: String = ""


class DetailPlayerActivity : AppCompatActivity(),ListPlayer {

    private lateinit var image_playera:ImageView
    private lateinit var name_player:TextView
    private lateinit var height_player:TextView
    private lateinit var weight_player:TextView
    private lateinit var pos_player:TextView
    private lateinit var desc_player:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_player)

        val intent = intent
        idPlayer = intent.getStringExtra("idPlayer")
        val apiRepository = ApiRepository()
        val gson = Gson()
        Toast.makeText(this, idPlayer.toString(),Toast.LENGTH_SHORT).show()
        presenter = ListPlayerPresenter(apiRepository,gson,this)



         image_playera = findViewById(R.id.image_player)
         name_player= findViewById(R.id.name_player)
         height_player= findViewById(R.id.height_player)
         weight_player= findViewById(R.id.weight_player)
         pos_player = findViewById(R.id.pos_player)
         desc_player= findViewById(R.id.desc_player)

        presenter.getDetailPlayer(idPlayer)

        name_player.text = "Ronaldo"

    }
    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showListPlayer(data: List<PlayerItem>) {

    }

    override fun showDetail(data: List<PlayersItem?>?) {
        val player = data!![0]
        Picasso.get().load(player!!.strFanart1.toString()).into(image_playera)
        name_player.text = player.strPlayer.toString()
        weight_player.text = player.strWeight.toString()
        height_player.text = player.strHeight.toString()
        pos_player.text = player.strPosition.toString()
        desc_player.text = player.strDescriptionEN.toString()

        Toast.makeText(this,"halo",Toast.LENGTH_SHORT).show()
    }
}
