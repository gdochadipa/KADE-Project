package co.ocha.eplmatch.Model.Player


import com.google.gson.annotations.SerializedName


data class PlayerResponse(

	@field:SerializedName("player")
	val player: List<PlayerItem>
)