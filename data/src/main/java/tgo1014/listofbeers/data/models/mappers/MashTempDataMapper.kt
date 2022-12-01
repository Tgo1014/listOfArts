package tgo1014.listofbeers.data.models.mappers

import tgo1014.listofbeers.data.models.MashTempData
import tgo1014.listofbeers.domain.models.MashTempDomain

fun MashTempData.toDomain() = MashTempDomain(
    temp = temp?.toDomain(),
    duration = duration
)