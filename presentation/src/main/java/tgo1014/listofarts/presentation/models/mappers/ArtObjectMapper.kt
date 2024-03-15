package tgo1014.listofarts.presentation.models.mappers

import tgo1014.listofarts.domain.models.ArtObjectDomain
import tgo1014.listofarts.presentation.models.ArtObjectUi

fun ArtObjectDomain.toUi() = ArtObjectUi(
    id = this.id,
    title = this.title,
    longTitle = this.longTitle,
    imageUrl = this.webImage.url,
    imageWidth = this.webImage.width,
    imageHeight = this.webImage.height,
    description = this.plaqueDescriptionEnglish,
    principalMaker = this.principalMaker,
    materialsList = this.materialsList,
)