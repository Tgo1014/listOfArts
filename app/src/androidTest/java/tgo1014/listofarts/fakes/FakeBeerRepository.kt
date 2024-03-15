package tgo1014.listofarts.fakes

import tgo1014.listofarts.domain.repositories.ArtRepository

class FakeBeerRepository(
    var beersToReturn: List<BeerDomain> = emptyList(),
    var throwException: Boolean = false,
) : ArtRepository {

    var page: Int? = null
    var search: String? = null
    var yeast: String? = null

    override suspend fun getArt(page: Int, query: String?, type: String?): List<BeerDomain> {
        this.page = page
        this.search = query
        this.yeast = type
        if (throwException) throw Exception()
        return if (query.isNullOrBlank()) {
            beersToReturn
        } else {
            beersToReturn.filter { it.name?.contains(query) == true }
        }
    }

    override suspend fun getBeerById(id: Int): BeerDomain {
        return beersToReturn.first()
    }
}