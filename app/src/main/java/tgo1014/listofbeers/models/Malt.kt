package tgo1014.listofbeers.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Malt(
    @SerialName("name")
    val name: String? = null,
    @SerialName("amount")
    val amount: Amount? = null
)