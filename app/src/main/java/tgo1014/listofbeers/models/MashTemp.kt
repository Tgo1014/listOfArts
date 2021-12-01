package tgo1014.listofbeers.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MashTemp(
    val duration: Int,
    val temp: Temp,
)