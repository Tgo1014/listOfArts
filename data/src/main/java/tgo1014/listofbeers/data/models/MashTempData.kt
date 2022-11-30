package tgo1014.listofbeers.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MashTempData(
    @SerialName("temp")
    val temp: TempData? = null,
    @SerialName("duration")
    val duration: Int? = null
)