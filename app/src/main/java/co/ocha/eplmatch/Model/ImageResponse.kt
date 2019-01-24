package co.ocha.eplmatch.Model

import com.google.gson.annotations.SerializedName

data class ImageResponse(
        @SerializedName("teams")
        val teams: List<Image>
)