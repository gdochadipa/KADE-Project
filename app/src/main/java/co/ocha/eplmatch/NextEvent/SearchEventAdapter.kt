package co.ocha.eplmatch.NextEvent

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import co.ocha.eplmatch.Model.Search.EventItem
import co.ocha.eplmatch.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk25.coroutines.onClick


class SearchEventAdapter(private val nexts: List<EventItem?>?, private var listener: (EventItem) -> Unit): RecyclerView.Adapter<SearchEventViewHolder>(){
    override fun onBindViewHolder(holder: SearchEventViewHolder, position: Int) {
        holder.bindItem(nexts!![position],listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchEventViewHolder {
        return SearchEventViewHolder(EventUI().createView(AnkoContext.Companion.create(parent.context,parent)))
    }

    override fun getItemCount(): Int = nexts!!.size

}
class EventUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui){

            linearLayout {

                lparams(width= matchParent,height = wrapContent)
                padding = dip(5)
                orientation = LinearLayout.HORIZONTAL

                cardView {
                    linearLayout {
                        orientation = LinearLayout.VERTICAL
                        textView(){
                            id = R.id.nameEvent
                            textSize = 13f
                            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                        }.lparams{
                            topMargin = dip(10)
                            bottomMargin = dip(10)
                            leftMargin = dip(5)
                            leftMargin = dip(5)
                        }
                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            orientation = LinearLayout.HORIZONTAL
                            textView(){
                                id = R.id.scoreHome
                                textSize = 12f
                                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                            }.lparams{
                                topMargin = dip(5)
                                bottomMargin = dip(5)

                            }
                            textView("-"){

                                textSize = 13f
                                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                            }.lparams{
                                topMargin = dip(5)
                                bottomMargin = dip(5)

                            }
                            textView(){
                                id = R.id.scoreAway
                                textSize = 12f
                                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                            }.lparams{
                                topMargin = dip(5)
                                bottomMargin = dip(5)

                            }
                        }

                        textView(){
                            id = R.id.strDate
                            textSize = 11f
                            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                        }.lparams{
                            topMargin = dip(10)
                            bottomMargin = dip(10)
                            leftMargin = dip(5)
                            leftMargin = dip(5)
                        }
                    }
                }.lparams(width= matchParent,height = matchParent){
                    margin=dip(5)
                }

            }
        }
    }
}

class SearchEventViewHolder(view: View): RecyclerView.ViewHolder(view){
    private val nameEvent: TextView = view.findViewById(R.id.nameEvent)
    private val strDate: TextView = view.findViewById(R.id.strDate)
    private val homeScore: TextView = view.findViewById(R.id.scoreHome)
    private val awayScore: TextView = view.findViewById(R.id.scoreAway)
    var context = view.context

    fun bindItem(nexts: EventItem?, listener: (EventItem) -> Unit){
        nameEvent.text = nexts!!.strEvent.toString()
        strDate.text = nexts!!.strDate.toString()
        homeScore.text = nexts!!.intHomeScore.toString()
        awayScore.text = nexts!!.intAwayScore.toString()

        itemView.onClick {
            listener(nexts)
        }

    }

}