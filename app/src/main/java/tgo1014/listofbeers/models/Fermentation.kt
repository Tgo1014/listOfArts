package tgo1014.listofbeers.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Fermentation(
    val temp: Temp? = Temp(),
)