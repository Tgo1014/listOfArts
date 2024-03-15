package tgo1014.listofbeers.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClassificationResponse(
    @SerialName("iconClassIdentifier")
    val iconClassIdentifier: List<String?>? = null,
    @SerialName("iconClassDescription")
    val iconClassDescription: List<String?>? = null,
    @SerialName("people")
    val people: List<String?>? = null,
    @SerialName("objectNumbers")
    val objectNumbers: List<String?>? = null
)