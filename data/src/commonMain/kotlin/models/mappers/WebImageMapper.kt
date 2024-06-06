package models.mappers

import models.WebImageResponse
import tgo1014.listofarts.domain.models.WebImageDomain

fun WebImageResponse.toDomain() = WebImageDomain(
    guid = this.guid.orEmpty(),
    width = this.width ?: 0,
    height = this.height ?: 0,
    url = this.url.orEmpty()
)