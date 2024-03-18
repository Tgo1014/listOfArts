package tgo1014.listofarts.domain.models

data class ArtObjectDomain(
    val links: LinksDomain = LinksDomain(),
    val id: String = "",
    val objectNumber: String = "",
    val title: String = "",
    val hasImage: Boolean = false,
    val principalOrFirstMaker: String = "",
    val longTitle: String = "",
    val showImage: Boolean = false,
    val permitDownload: Boolean = false,
    val webImage: WebImageDomain = WebImageDomain(),
    val headerImage: HeaderImageDomain = HeaderImageDomain(),
    val productionPlaces: List<String> = listOf(),
    val plaqueDescriptionEnglish: String = "",
    val principalMaker: String = "",
    val materialsList: List<String> = emptyList(),
    val mainType: String = "",
)
