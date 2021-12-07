package tgo1014.listofbeers.models

import kotlinx.serialization.Serializable

@Serializable
data class MashTemp(
    val duration: Int? = 0,
    val temp: Temp? = Temp(),
)