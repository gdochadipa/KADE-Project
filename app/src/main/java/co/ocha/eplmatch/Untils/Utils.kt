package co.ocha.eplmatch.Untils

import android.view.View
import java.text.SimpleDateFormat

fun View.visible(){
    visibility = View.VISIBLE
}

fun View.invisible(){
    visibility = View.INVISIBLE
}

class Utils{
    companion object {
        fun converterDate(date: String?):String{
          var sdf= SimpleDateFormat("yyyy-MM-dd")
            var newDate = sdf.parse(date)
            sdf = SimpleDateFormat("EEE, dd MMMM yyyy")
            return sdf.format(newDate)
        }
    }
}
