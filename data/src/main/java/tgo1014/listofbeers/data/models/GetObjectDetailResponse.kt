package tgo1014.listofbeers.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetObjectDetailResponse(
    @SerialName("elapsedMilliseconds")
    val elapsedMilliseconds: Int? = 0,
    @SerialName("artObject")
    val artObject: ArtObjectResponse? = ArtObjectResponse(),
    @SerialName("artObjectPage")
    val artObjectPage: ArtObjectPageResponse? = ArtObjectPageResponse()
)