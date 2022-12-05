package tgo1014.listofbeers.data.models.mappers

import tgo1014.listofbeers.data.models.TempData
import tgo1014.listofbeers.domain.models.TempDomain

fun TempData.toDomain() = TempDomain(
    value = value,
    unit = unit
)