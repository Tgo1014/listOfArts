package tgo1014.listofbeers.data.repositories

import tgo1014.listofbeers.data.extensions.successOrThrow
import tgo1014.listofbeers.data.models.mappers.toDomain
import tgo1014.listofbeers.data.network.PunkApi
import tgo1014.listofbeers.domain.models.BeerDomain
import tgo1014.listofbeers.domain.repositories.BeersRepository
import javax.inject.Inject

class BeersRepositoryImpl @Inject constructor(
    private val punkApi: PunkApi
) : BeersRepository {

    override suspend fun getBeers(page: Int, search: String?, yeast: String?): List<BeerDomain> {
        val response = punkApi.getBeers(
            page = page,
            beerName = search,
            yeast = yeast
        )
        return response.successOrThrow().map { it.toDomain() }
    }

    override suspend fun getBeerById(id: Int): BeerDomain {
        return punkApi.getBeerById(id).successOrThrow().toDomain()
    }
}