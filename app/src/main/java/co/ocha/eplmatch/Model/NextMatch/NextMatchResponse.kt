package co.ocha.eplmatch.Model.NextMatch


import co.ocha.eplmatch.Model.NextMatch.NextEventsItem
import com.google.gson.annotations.SerializedName


data class NextMatchResponse(

	@field:SerializedName("events")
	val events: List<NextEventsItem?>?
)