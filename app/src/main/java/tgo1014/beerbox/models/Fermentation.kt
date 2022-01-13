package tgo1014.beerbox.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Fermentation(
    @SerialName("temp")
    val temp: Temp? = null
)