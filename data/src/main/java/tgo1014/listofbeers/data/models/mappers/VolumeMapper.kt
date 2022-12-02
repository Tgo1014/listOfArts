package tgo1014.listofbeers.data.models.mappers

import tgo1014.listofbeers.data.models.VolumeData
import tgo1014.listofbeers.domain.models.VolumeDomain

fun VolumeData.toDomain() = VolumeDomain(
    value = value,
    unit = unit
)