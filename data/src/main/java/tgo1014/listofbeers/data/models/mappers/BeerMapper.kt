package tgo1014.listofbeers.data.models.mappers

import tgo1014.listofbeers.data.models.BeerData
import tgo1014.listofbeers.domain.models.BeerDomain

fun BeerData.toDomain() = BeerDomain(
    id = id,
    name = name,
    tagline = tagline,
    firstBrewed = firstBrewed,
    description = description,
    imageUrl = imageUrl,
    abv = abv,
    ibu = ibu,
    targetFg = targetFg,
    targetOg = targetOg,
    ebc = ebc,
    srm = srm,
    ph = ph,
    attenuationLevel = attenuationLevel,
    volume = volume?.toDomain(),
    boilVolume = boilVolume?.toDomain(),
    method = method?.toDomain(),
    ingredients = ingredients?.toDomain(),
    foodPairing = foodPairing,
    brewersTips = brewersTips,
    contributedBy = contributedBy
)