package tgo1014.listofbeers.data.models.mappers

import tgo1014.listofbeers.data.models.MethodData
import tgo1014.listofbeers.domain.models.MethodDomain

fun MethodData.toDomain() = MethodDomain(
    mashTemp = mashTemp?.map { it.toDomain() },
    fermentation = fermentation?.toDomain(),
    twist = twist
)