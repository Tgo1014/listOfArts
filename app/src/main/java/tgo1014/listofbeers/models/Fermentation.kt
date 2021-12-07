package tgo1014.listofbeers.models

import kotlinx.serialization.Serializable

@Serializable
data class Fermentation(
    val temp: Temp? = Temp(),
)