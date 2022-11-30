package tgo1014.listofbeers.domain.repositories

import tgo1014.listofbeers.domain.models.BeerDomain

interface BeersRepository {
    suspend fun getBeers(
        page: Int,
        search: String? = null,
        yeast: String? = null
    ): List<BeerDomain>
}