package tgo1014.listofbeers.data.models.mappers

import tgo1014.listofbeers.data.models.AmountData
import tgo1014.listofbeers.domain.models.AmountDomain

fun AmountData.toDomain() = AmountDomain(
    value = value,
    unit = unit
)