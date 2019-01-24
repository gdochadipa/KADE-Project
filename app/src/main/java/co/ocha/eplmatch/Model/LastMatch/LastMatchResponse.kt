package co.ocha.eplmatch.Model.LastMatch


import co.ocha.eplmatch.Model.LastMatch.LastEventsItem
import com.google.gson.annotations.SerializedName


data class LastMatchResponse(

	@field:SerializedName("events")
	val events: List<LastEventsItem>
)