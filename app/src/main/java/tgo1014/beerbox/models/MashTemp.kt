package tgo1014.beerbox.models

import kotlinx.serialization.Serializable

@Serializable
data class MashTemp(
    val duration: Int? = 0,
    val temp: Temp? = Temp(),
)