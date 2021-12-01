package tgo1014.listofbeers.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Volume(
    val unit: String,
    val value: Int,
)