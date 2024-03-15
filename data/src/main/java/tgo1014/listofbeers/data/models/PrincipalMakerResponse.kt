package tgo1014.listofbeers.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PrincipalMakerResponse(
    @SerialName("name")
    val name: String? = null,
    @SerialName("unFixedName")
    val unFixedName: String? = null,
    @SerialName("placeOfBirth")
    val placeOfBirth: String? = null,
    @SerialName("dateOfBirth")
    val dateOfBirth: String? = null,
    @SerialName("dateOfDeath")
    val dateOfDeath: String? = null,
    @SerialName("placeOfDeath")
    val placeOfDeath: String? = null,
    @SerialName("occupation")
    val occupation: List<String?>? = null,
    @SerialName("roles")
    val roles: List<String?>? = null,
    @SerialName("nationality")
    val nationality: String? = null,
    @SerialName("labelDesc")
    val labelDesc: String? = null
)