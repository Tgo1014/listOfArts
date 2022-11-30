package tgo1014.listofbeers.domain.models

data class MethodDomain(
    val mashTemp: List<MashTempDomain>? = listOf(),
    val fermentation: FermentationDomain? = FermentationDomain(),
    val twist: String? = null
)