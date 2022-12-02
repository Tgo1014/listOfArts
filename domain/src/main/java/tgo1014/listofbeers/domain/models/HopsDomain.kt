package tgo1014.listofbeers.domain.models

data class HopsDomain(
    val amount: AmountDomain? = AmountDomain(),
    val name: String? = "",
    val add: String? = "",
    val attribute: String? = "",
)
