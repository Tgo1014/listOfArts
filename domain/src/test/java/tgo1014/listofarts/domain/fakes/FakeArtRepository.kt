package tgo1014.listofarts.domain.fakes

import tgo1014.listofarts.domain.models.ArtObjectDomain
import tgo1014.listofarts.domain.repositories.ArtRepository

class FakeArtRepository(
    var artsToReturn: List<ArtObjectDomain> = emptyList(),
    var throwException: Boolean = false,
) : ArtRepository {

    var page: Int? = null
    var search: String? = null
    var type: String? = null

    override suspend fun getArt(page: Int, query: String?, type: String?): List<ArtObjectDomain> {
        this.page = page
        this.search = query
        this.type = type
        if (throwException) throw Exception()
        return artsToReturn
    }

    override suspend fun getArtById(id: String): ArtObjectDomain {
        return artsToReturn.first()
    }
}
