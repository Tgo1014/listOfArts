package tgo1014.listofbeers.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Method(
    val fermentation: Fermentation = Fermentation(),
    @Json(name = "mash_temp")
    val mashTemp: List<MashTemp> = listOf(),
    val twist: Any = Any(),
)