package tgo1014.listofbeers.data.repositories

import tgo1014.listofbeers.data.extensions.successOrThrow
import tgo1014.listofbeers.data.models.mappers.toDomain
import tgo1014.listofbeers.data.network.PunkApi
import tgo1014.listofbeers.data.network.RijksmMuseumApi
import tgo1014.listofbeers.domain.models.BeerDomain
import tgo1014.listofbeers.domain.repositories.BeersRepository
import javax.inject.Inject

class BeersRepositoryImpl @Inject constructor(
    private val punkApi: PunkApi,
    private val rijksmMuseumApi: RijksmMuseumApi,
) : BeersRepository {

    override suspend fun getBeers(page: Int, search: String?, yeast: String?): List<BeerDomain> {
        val response = rijksmMuseumApi.getCollections(page, search)
        return response.successOrThrow().artObjects.orEmpty().map {
            BeerDomain(id = it.id , name = it.title.orEmpty(), imageUrl = it.webImage?.url)
        }
//        val response = punkApi.getBeers(
//            page = page,
//            beerName = search,
//            yeast = yeast
//        )
       // return response.successOrThrow().map { it.toDomain() }
    }

    override suspend fun getBeerById(id: Int): BeerDomain {
        return punkApi.getBeerById(id).successOrThrow().first().toDomain()
    }
}