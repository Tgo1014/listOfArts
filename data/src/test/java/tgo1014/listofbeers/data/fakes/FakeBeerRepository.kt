package tgo1014.listofbeers.data.fakes

import tgo1014.listofbeers.domain.models.BeerDomain
import tgo1014.listofbeers.domain.repositories.BeersRepository

class FakeBeerRepository(
    var beersToReturn: List<BeerDomain> = emptyList(),
    var throwException: Boolean = false,
) : BeersRepository {

    var page: Int? = null
    var search: String? = null
    var yeast: String? = null

    override suspend fun getBeers(page: Int, search: String?, yeast: String?): List<BeerDomain> {
        this.page = page
        this.search = search
        this.yeast = yeast
        if (throwException) throw Exception()
        return beersToReturn
    }

    override suspend fun getBeerById(id: Int): BeerDomain {
        return beersToReturn.first()
    }
}