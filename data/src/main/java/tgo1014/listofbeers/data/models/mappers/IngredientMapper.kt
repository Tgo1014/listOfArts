package tgo1014.listofbeers.data.models.mappers

import tgo1014.listofbeers.data.models.IngredientsData
import tgo1014.listofbeers.domain.models.IngredientsDomain

fun IngredientsData.toDomain() = IngredientsDomain(
    malt = this.malt?.map { it.toDomain() },
    hops = this.hops?.map { it.toDomain() },
    yeast = this.yeast,
)