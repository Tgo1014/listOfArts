package tgo1014.listofbeers.presentation.models.mappers

import tgo1014.listofbeers.domain.models.BeerDomain
import tgo1014.listofbeers.presentation.models.BeerUi

fun BeerDomain.toUi() = BeerUi(
    id = this.id ?: -1,
    name = this.name.orEmpty(),
    tagline = this.tagline.orEmpty(),
    firstBrewed = this.firstBrewed.orEmpty(),
    description = this.description.orEmpty(),
    foodParingList = this.foodPairing.orEmpty(),
    brewersTips = this.brewersTips.orEmpty(),
    imageUrl = this.imageUrl.orEmpty(),
)