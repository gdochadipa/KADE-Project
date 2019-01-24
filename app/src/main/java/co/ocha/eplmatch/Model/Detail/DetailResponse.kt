package co.ocha.eplmatch.Model.Detail


import com.google.gson.annotations.SerializedName


data class DetailResponse(

	@field:SerializedName("players")
	val players: List<PlayersItem?>?
)