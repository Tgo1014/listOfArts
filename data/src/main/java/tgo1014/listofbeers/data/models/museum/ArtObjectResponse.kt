package tgo1014.listofbeers.data.models.museum


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtObjectResponse(
    @SerialName("links")
    val links: LinksResponse? = null,
    @SerialName("id")
    val id: String? = null,
    @SerialName("objectNumber")
    val objectNumber: String? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("hasImage")
    val hasImage: Boolean? = null,
    @SerialName("principalOrFirstMaker")
    val principalOrFirstMaker: String? = null,
    @SerialName("longTitle")
    val longTitle: String? = null,
    @SerialName("showImage")
    val showImage: Boolean? = null,
    @SerialName("permitDownload")
    val permitDownload: Boolean? = null,
    @SerialName("webImage")
    val webImage: WebImageResponse? = null,
    @SerialName("headerImage")
    val headerImage: HeaderImageResponse? = null,
    @SerialName("productionPlaces")
    val productionPlaces: List<String?>? = null
)