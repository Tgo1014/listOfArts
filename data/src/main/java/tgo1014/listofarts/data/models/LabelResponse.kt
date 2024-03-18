package tgo1014.listofarts.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LabelResponse(
    @SerialName("title")
    val title: String? = null,
    @SerialName("makerLine")
    val makerLine: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("notes")
    val notes: String? = null,
    @SerialName("date")
    val date: String? = null
)