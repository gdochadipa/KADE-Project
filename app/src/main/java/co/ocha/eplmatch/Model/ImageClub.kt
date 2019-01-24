package co.ocha.eplmatch.Model

import com.google.gson.annotations.SerializedName

data class Image(
        @SerializedName("idTeam")
        var teamId: String? = null,

        @SerializedName("strTeamBadge")
        var teamBandage: String? = null
)