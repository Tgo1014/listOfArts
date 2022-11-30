package tgo1014.listofbeers.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MaltData(
    @SerialName("name")
    val name: String? = null,
    @SerialName("amount")
    val amount: AmountData? = null
)