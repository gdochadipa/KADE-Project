package co.ocha.eplmatch.Model


import com.google.gson.annotations.SerializedName


data class DetailResponse(

	@field:SerializedName("events")
	val events: List<DetEventsItem>
)