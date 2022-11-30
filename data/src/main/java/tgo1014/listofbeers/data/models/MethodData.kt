package tgo1014.listofbeers.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MethodData(
    @SerialName("mash_temp")
    val mashTemp: List<MashTempData>? = listOf(),
    @SerialName("fermentation")
    val fermentation: FermentationData? = FermentationData(),
    @SerialName("twist")
    val twist: String? = ""
)