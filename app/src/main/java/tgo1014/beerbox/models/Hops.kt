package tgo1014.beerbox.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Hops(
    @SerialName("amount")
    val amount: Amount? = Amount(),
    @SerialName("name")
    val name: String? = "",
    @SerialName("add")
    val add: String? = "",
    @SerialName("attribute")
    val attribute: String? = "",
)