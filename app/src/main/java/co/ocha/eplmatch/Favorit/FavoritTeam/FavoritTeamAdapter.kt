package co.ocha.eplmatch.Favorit.FavoritTeam

import android.support.v7.widget.RecyclerView
import co.ocha.eplmatch.Db.FavoritTeamDB.FavoritTeam
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import co.ocha.eplmatch.R
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk25.coroutines.onClick

class FavoritTeamAdapter(private var favorit: List<FavoritTeam>,private val listener:(FavoritTeam)->Unit)
    :RecyclerView.Adapter<FavoritTeamViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritTeamViewHolder {
       return FavoritTeamViewHolder(FavoritTeamUI().createView(AnkoContext.Companion.create(parent.context,parent)))
    }

    override fun getItemCount(): Int {
        return favorit.size
    }

    override fun onBindViewHolder(holder: FavoritTeamViewHolder, position: Int) {
       holder.bindItem(favorit[position],listener)
    }
}
class FavoritTeamUI: AnkoComponent<ViewGroup>{
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui){
            relativeLayout {
                lparams(width= matchParent,height = wrapContent)
                padding = dip(5)
                cardView {
                    linearLayout {
                        lparams(width= matchParent,height = wrapContent)
                        orientation = LinearLayout.HORIZONTAL
                        padding = dip(5)
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
                }.lparams(width= matchParent,height = wrapContent)
            }
        }
    }
}

class FavoritTeamViewHolder(view:View): RecyclerView.ViewHolder(view){
   var teamBadge2:ImageView = view.findViewById(R.id.team_badge)
    var teamName:TextView = view.findViewById(R.id.team_name)

    fun bindItem(favorit: FavoritTeam,listener: (FavoritTeam) -> Unit){
        Picasso.get().load(favorit.strTeamBadge).into(teamBadge2)
        teamName.text = favorit.strTeam
        itemView.onClick {
            listener(favorit)
        }
    }
}