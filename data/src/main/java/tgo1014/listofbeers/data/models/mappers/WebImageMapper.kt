package tgo1014.listofbeers.data.models.mappers

import tgo1014.listofbeers.data.models.WebImageResponse
import tgo1014.listofbeers.domain.models.WebImageDomain

fun WebImageResponse.toDomain() = WebImageDomain(
    guid = this.guid.orEmpty(),
    width = this.width ?: 0,
    height = this.height ?: 0,
    url = this.url.orEmpty()
)