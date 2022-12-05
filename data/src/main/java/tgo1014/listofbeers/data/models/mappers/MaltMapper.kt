package tgo1014.listofbeers.data.models.mappers

import tgo1014.listofbeers.data.models.MaltData
import tgo1014.listofbeers.domain.models.MaltDomain

fun MaltData.toDomain() = MaltDomain(
    name = this.name,
    amount = this.amount?.toDomain()
)