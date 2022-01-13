package tgo1014.beerbox.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MashTemp(
    @SerialName("temp")
    val temp: Temp? = null,
    @SerialName("duration")
    val duration: Int? = null
)