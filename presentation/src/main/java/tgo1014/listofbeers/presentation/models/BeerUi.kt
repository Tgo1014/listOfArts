package tgo1014.listofbeers.presentation.models

data class BeerUi(
    val id: String = "",
    val name: String = "",
    val tagline: String = "",
    val firstBrewed: String = "",
    val description: String = "",
    val foodParingList: List<String> = emptyList(),
    val brewersTips: String = "",
    val imageUrl: String = "",
    val imageHeight: Int = 0,
    val imageWidth: Int = 0,
)
