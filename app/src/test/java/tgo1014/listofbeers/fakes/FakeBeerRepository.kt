package tgo1014.listofbeers.fakes

import tgo1014.listofbeers.models.Beer
import tgo1014.listofbeers.domain.repositories.BeersRepository

class FakeBeerRepository(var beersToReturn: List<Beer> = emptyList()) : BeersRepository {
    var throwException = false
    override suspend fun getBeers(page: Int, search: String?, yeast: String?): List<Beer> {
        if (throwException) throw Exception()
        return beersToReturn
    }
}