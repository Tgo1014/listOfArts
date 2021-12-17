package tgo1014.beerbox.models

import kotlinx.serialization.Serializable

@Serializable
data class Hops(
    val amount: Amount? = Amount(),
    val name: String? = "",
    val add: String? = "",
    val attribute: String? = "",
)