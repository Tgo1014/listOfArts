package tgo1014.listofarts.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DatingResponse(
    @SerialName("presentingDate")
    val presentingDate: String? = null,
    @SerialName("sortingDate")
    val sortingDate: Int? = null,
    @SerialName("period")
    val period: Int? = null,
    @SerialName("yearEarly")
    val yearEarly: Int? = null,
    @SerialName("yearLate")
    val yearLate: Int? = null
)