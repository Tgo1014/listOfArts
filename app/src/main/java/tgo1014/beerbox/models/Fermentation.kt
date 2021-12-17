package tgo1014.beerbox.models

import kotlinx.serialization.Serializable

@Serializable
data class Fermentation(
    val temp: Temp? = Temp(),
)