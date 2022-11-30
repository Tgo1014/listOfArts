package tgo1014.listofbeers.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FermentationData(
    @SerialName("temp")
    val temp: TempDataX? = TempDataX()
)