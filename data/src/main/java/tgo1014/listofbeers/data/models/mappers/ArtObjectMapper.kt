package tgo1014.listofbeers.data.models.mappers

import tgo1014.listofbeers.data.models.ArtObjectResponse
import tgo1014.listofbeers.domain.models.ArtObjectDomain
import tgo1014.listofbeers.domain.models.WebImageDomain

fun ArtObjectResponse.toDomain() = ArtObjectDomain(
    id = this.id.orEmpty(),
    title = this.title.orEmpty(),
    longTitle = this.longTitle.orEmpty(),
    objectNumber = this.objectNumber.orEmpty(),
    webImage = this.webImage?.toDomain() ?: WebImageDomain(),
    plaqueDescriptionEnglish = if (!this.plaqueDescriptionEnglish.isNullOrBlank()) {
        this.plaqueDescriptionEnglish
    } else {
        this.label?.description.orEmpty()
    },
    principalMaker = this.principalMaker.orEmpty(),
    materialsList = materials.orEmpty()
)