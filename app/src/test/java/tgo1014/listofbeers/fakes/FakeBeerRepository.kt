package tgo1014.listofbeers.fakes

import tgo1014.listofbeers.models.Beer
import tgo1014.listofbeers.repositories.BeersRepository

class FakeBeerRepository : BeersRepository {
    var beersToReturn = emptyList<Beer>()
    override suspend fun getBeers(page: Int, search: String?, yeast: String?) = beersToReturn
}