package co.ocha.eplmatch.Favorit.FavoritMatch

import android.content.Intent
import android.provider.AlarmClock
import android.support.v7.widget.RecyclerView
import co.ocha.eplmatch.Db.Favorit
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import co.ocha.eplmatch.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class FavoritAdapter(private val favorit: List<Favorit>, private val listener: (Favorit) -> Unit)
    :RecyclerView.Adapter<FavoritViewHolder>(){


    override fun onBindViewHolder(holder: FavoritViewHolder, position: Int) {
        holder.bindItem(favorit[position],listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritViewHolder {
    return FavoritViewHolder(FavoritUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int {
        return favorit.size
    }

}

class FavoritUI: AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
       return with(ui){
           linearLayout {

               lparams(width= matchParent,height = wrapContent)
               padding = dip(5)
               orientation = LinearLayout.HORIZONTAL

               cardView {
                   relativeLayout {

                       imageView(R.drawable.ic_add_alert_black_24dp){
                           id =  R.id.add_to_alarm
                       }.lparams(width=dip(20),height = dip(20)){
                           alignParentRight()
                           alignParentTop()
                           margin= dip(5)
                       }


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
                               width = matchParent
                               height= wrapContent
                           }
                           linearLayout {
                               weightSum = 3f
                               lparams(width = matchParent, height = wrapContent)
                               orientation = LinearLayout.HORIZONTAL
                               textView(){

                                   id = R.id.scoreHome
                                   textSize = 12f
                                   textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                               }.lparams{
                                   weight = 1.2f
                                   topMargin = dip(5)
                                   bottomMargin = dip(5)

                               }
                               textView("-"){

                                   textSize = 13f
                                   textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                               }.lparams{
                                   topMargin = dip(5)
                                   bottomMargin = dip(5)
                                   weight = 0.6f
                               }
                               textView(){
                                   id = R.id.scoreAway
                                   textSize = 12f
                                   textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                               }.lparams{
                                   topMargin = dip(5)
                                   bottomMargin = dip(5)
                                   weight=1.2f
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
                               width = matchParent
                               height= wrapContent
                           }
                       }
                   }.lparams(width= matchParent,height = matchParent){
                       margin=dip(5)
                   }

               }.lparams(width= matchParent,height = matchParent)
           }
       }
    }
}

class FavoritViewHolder(view:View): RecyclerView.ViewHolder(view){

    private val nameEvent: TextView = view.findViewById(R.id.nameEvent)
    private val strDate: TextView = view.findViewById(R.id.strDate)
    private val homeScore: TextView = view.findViewById(R.id.scoreHome)
    private val awayScore: TextView = view.findViewById(R.id.scoreAway)

    var context= view.context
    private val alarm: ImageView =  view.findViewById(R.id.add_to_alarm)

    private var tanggal = ""
    var time = "20:00:00+00:00"
    var jam = time.substring(0,8)

    val Final_formater = SimpleDateFormat("MMM d, h:mm a")
    val formater_year = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

    val currentDate =  formater_year.format(Date())
    var today = formater_year.parse(currentDate)

    fun bindItem(favorit: Favorit, listener: (Favorit) -> Unit){
        nameEvent.text = favorit.strEvent.toString()

        homeScore.text = favorit.intHomeScore.toString()
        awayScore.text = favorit.intAwayScore.toString()

        val timeZone = Calendar.getInstance().timeZone
        val zone = TimeZone.getTimeZone("UTC")
        jam = favorit.strTime.toString().substring(0,8)
        tanggal = "${favorit.dateEvent}T${jam}"

        formater_year.timeZone = zone
        val value = formater_year.parse(tanggal)

        Final_formater.timeZone = TimeZone.getDefault()
        tanggal = Final_formater.format(value)

        strDate.text = "${tanggal}"

        itemView.onClick {
            listener(favorit)
        }
        val selisihMS = value.time - today.time
        val beda = TimeUnit.HOURS.convert(selisihMS, TimeUnit.MILLISECONDS)
        val intent = Intent(AlarmClock.ACTION_SET_ALARM)

        alarm.setOnClickListener {
            intent.putExtra(AlarmClock.EXTRA_MESSAGE,favorit.strEvent)
            intent.putExtra(AlarmClock.EXTRA_HOUR,beda)
            context.startActivity(intent)
        }
    }
}