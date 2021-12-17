package tgo1014.beerbox.models

import kotlinx.serialization.Serializable

@Serializable
data class Malt(
    val amount: Amount? = Amount(),
    val name: String? = "",
)