package tgo1014.listofbeers.data.models.mappers

import tgo1014.listofbeers.data.models.HopsData
import tgo1014.listofbeers.domain.models.HopsDomain

fun HopsData.toDomain() = HopsDomain(
    amount = amount?.toDomain(),
    name = name,
    add = add,
    attribute = attribute
)