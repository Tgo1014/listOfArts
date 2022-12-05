package tgo1014.listofbeers.data.models.mappers

import tgo1014.listofbeers.data.models.FermentationData
import tgo1014.listofbeers.domain.models.FermentationDomain

fun FermentationData.toDomain() = FermentationDomain(
    temp = temp?.toDomain()
)