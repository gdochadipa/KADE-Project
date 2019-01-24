package co.ocha.eplmatch.Model.Search


import com.google.gson.annotations.SerializedName


data class SearchMatchResponse(

	@field:SerializedName("event")
	val event: List<EventItem?>? = null
)