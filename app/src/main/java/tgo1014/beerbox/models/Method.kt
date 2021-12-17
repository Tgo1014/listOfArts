package tgo1014.beerbox.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Method(
    val fermentation: Fermentation? = Fermentation(),
    @SerialName("mash_temp")
    val mashTemp: List<MashTemp>? = listOf(),
    val twist: String? = "",
)