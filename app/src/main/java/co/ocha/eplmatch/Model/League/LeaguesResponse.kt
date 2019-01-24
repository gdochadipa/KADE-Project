package co.ocha.eplmatch.Model.League


import com.google.gson.annotations.SerializedName


data class LeaguesResponse(

	@field:SerializedName("leagues")
	val leagues: List<LeaguesItem>
)