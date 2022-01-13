package tgo1014.beerbox.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Method(
    @SerialName("mash_temp")
    val mashTemp: List<MashTemp>? = null,
    @SerialName("fermentation")
    val fermentation: Fermentation? = null,
    @SerialName("twist")
    val twist: String? = null
)