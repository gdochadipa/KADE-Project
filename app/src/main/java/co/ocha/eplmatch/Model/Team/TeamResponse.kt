package co.ocha.eplmatch.Model.Team

import com.google.gson.annotations.SerializedName


data class TeamResponse(

	@field:SerializedName("teams")
	val teams: List<TeamsItem?>?
)