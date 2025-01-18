package presentation.models.mappers

import tgo1014.listofarts.domain.models.ArtObjectDomain
import presentation.models.ArtObjectUi

fun ArtObjectDomain.toUi() = ArtObjectUi(
    id = this.id,
    title = this.title.trim(),
    longTitle = this.longTitle.trim(),
    imageUrl = this.webImage.url,
    imageWidth = this.webImage.width,
    imageHeight = this.webImage.height,
    description = this.plaqueDescriptionEnglish,
    principalMaker = this.principalMaker,
    materialsList = this.materialsList,
)