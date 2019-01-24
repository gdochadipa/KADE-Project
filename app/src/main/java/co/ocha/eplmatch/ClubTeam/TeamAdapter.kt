package co.ocha.eplmatch.ClubTeam

import android.content.Context
import android.support.v7.widget.RecyclerView
import co.ocha.eplmatch.Model.Team.TeamsItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import co.ocha.eplmatch.R
import com.squareup.picasso.Picasso
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class TeamAdapter(private val teams: List<TeamsItem?>?, private val listener:(TeamsItem?)->Unit)
    : RecyclerView.Adapter<TeamViewHolder>(){
    override fun getItemCount(): Int {
        return teams!!.size
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItems(teams!![position],listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(TeamUI().createView(AnkoContext.Companion.create(parent.context,parent)))
    }

}

class TeamUI: AnkoComponent<ViewGroup>{
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
                    textSize = 16f
                }.lparams{
                    margin=dip(15)
                }
            }
        }
    }
}
class TeamViewHolder(view: View): RecyclerView.ViewHolder(view){
    private val teamBadge: ImageView = view.findViewById(R.id.team_badge)
    private val teamName: TextView = view.findViewById(R.id.team_name)
    private val context: Context = view.context
    fun bindItems(teams: TeamsItem?, listener: (TeamsItem?) -> Unit){
        Picasso.get().load(teams!!.strTeamBadge).into(teamBadge)
        teamName.text = teams!!.strTeam
        itemView.onClick { listener(teams) }
    }
}