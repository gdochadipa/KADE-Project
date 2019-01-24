package co.ocha.eplmatch.DetailClub.PlayerClub

import android.content.Context
import android.support.v7.view.menu.MenuView
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import co.ocha.eplmatch.Model.Player.PlayerItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import co.ocha.eplmatch.R
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class ListPlayerAdapter(private val players: List<PlayerItem>, private val listener: (PlayerItem)-> Unit)
    :RecyclerView.Adapter<PlayerViewHolder>(){
    override fun getItemCount(): Int {
        return players.size
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bindItems(players[position],listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
       return PlayerViewHolder(PlayerUI().createView(AnkoContext.Companion.create(parent.context,parent)))
    }

}

class PlayerUI: AnkoComponent<ViewGroup>{
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui){
            linearLayout {
                lparams(width= matchParent,height = wrapContent)
                padding = dip(16)
                orientation = LinearLayout.HORIZONTAL

                imageView(){
                    id = R.id.team_badge
                }.lparams{
                    height= dip(50)
                    width=dip(50)
                }

                textView(){
                    id = R.id.team_name
                    textSize = 14f
                }.lparams{
                    margin=dip(15)
                }

                textView(){
                    id = R.id.positionPlayer
                    textSize = 14f
                }.lparams{
                    margin=dip(15)
                }
            }
        }
    }
}

class PlayerViewHolder(view:View): RecyclerView.ViewHolder(view){
    private val playerImage: ImageView = view.findViewById(R.id.team_badge)
    private val playerName: TextView = view.findViewById(R.id.team_name)
    private val playerPos: TextView = view.findViewById(R.id.positionPlayer)
    private val context: Context = view.context
    fun bindItems(players: PlayerItem, listener: (PlayerItem) -> Unit){
        Picasso.get().load(players.strCutout).into(playerImage)
        playerName.text = players.strPlayer
        playerPos.text = players.strPosition
        itemView.onClick {
            listener(players)
        }
    }
}