package tgo1014.listofarts.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ArtObjectResponse(
    @SerialName("links")
    val links: LinksResponseX? = LinksResponseX(),
    @SerialName("id")
    val id: String? = "",
    @SerialName("priref")
    val priref: String? = "",
    @SerialName("objectNumber")
    val objectNumber: String? = "",
    @SerialName("language")
    val language: String? = "",
    @SerialName("title")
    val title: String? = "",
    @SerialName("webImage")
    val webImage: WebImageResponse? = WebImageResponse(),
    @SerialName("colors")
    val colors: List<ColorResponse>? = listOf(),
    @SerialName("colorsWithNormalization")
    val colorsWithNormalization: List<ColorsWithNormalizationResponse>? = listOf(),
    @SerialName("normalizedColors")
    val normalizedColors: List<NormalizedColorResponse>? = listOf(),
    @SerialName("titles")
    val titles: List<String>? = listOf(),
    @SerialName("description")
    val description: String? = "",
    @SerialName("objectTypes")
    val objectTypes: List<String>? = listOf(),
    @SerialName("objectCollection")
    val objectCollection: List<String>? = listOf(),
    @SerialName("principalMakers")
    val principalMakers: List<PrincipalMakerResponse>? = listOf(),
    @SerialName("plaqueDescriptionDutch")
    val plaqueDescriptionDutch: String? = "",
    @SerialName("plaqueDescriptionEnglish")
    val plaqueDescriptionEnglish: String? = "",
    @SerialName("principalMaker")
    val principalMaker: String? = "",
    @SerialName("acquisition")
    val acquisition: AcquisitionResponse? = AcquisitionResponse(),
    @SerialName("exhibitions")
    val exhibitions: List<String>? = listOf(),
    @SerialName("materials")
    val materials: List<String>? = listOf(),
    @SerialName("techniques")
    val techniques: List<String>? = listOf(),
    @SerialName("productionPlaces")
    val productionPlaces: List<String>? = listOf(),
    @SerialName("hasImage")
    val hasImage: Boolean? = false,
    @SerialName("historicalPersons")
    val historicalPersons: List<String>? = listOf(),
    @SerialName("documentation")
    val documentation: List<String>? = listOf(),
    @SerialName("principalOrFirstMaker")
    val principalOrFirstMaker: String? = "",
    @SerialName("dimensions")
    val dimensions: List<DimensionResponse>? = listOf(),
    @SerialName("physicalMedium")
    val physicalMedium: String? = "",
    @SerialName("longTitle")
    val longTitle: String? = "",
    @SerialName("subTitle")
    val subTitle: String? = "",
    @SerialName("scLabelLine")
    val scLabelLine: String? = "",
    @SerialName("label")
    val label: LabelResponse? = LabelResponse(),
    @SerialName("showImage")
    val showImage: Boolean? = false,
    @SerialName("location")
    val location: String? = ""
)