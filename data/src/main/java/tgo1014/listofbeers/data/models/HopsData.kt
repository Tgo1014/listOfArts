package tgo1014.listofbeers.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HopsData(
    @SerialName("amount")
    val amount: AmountData? = AmountData(),
    @SerialName("name")
    val name: String? = "",
    @SerialName("add")
    val add: String? = "",
    @SerialName("attribute")
    val attribute: String? = "",
)