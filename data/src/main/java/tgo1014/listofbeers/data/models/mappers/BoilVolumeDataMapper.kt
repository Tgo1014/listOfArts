package tgo1014.listofbeers.data.models.mappers

import tgo1014.listofbeers.data.models.BoilVolumeData
import tgo1014.listofbeers.domain.models.BoilVolumeDomain

fun BoilVolumeData.toDomain() = BoilVolumeDomain(
    value = value,
    unit = unit
)