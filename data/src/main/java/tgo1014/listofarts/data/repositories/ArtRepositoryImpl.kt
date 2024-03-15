package tgo1014.listofarts.data.repositories

import tgo1014.listofarts.data.extensions.successOrThrow
import tgo1014.listofarts.data.models.mappers.toDomain
import tgo1014.listofarts.data.network.RijksmMuseumApi
import tgo1014.listofarts.domain.models.ArtObjectDomain
import tgo1014.listofarts.domain.repositories.ArtRepository
import javax.inject.Inject

class ArtRepositoryImpl @Inject constructor(
    private val rijksmMuseumApi: RijksmMuseumApi,
) : ArtRepository {

    override suspend fun getArt(page: Int, query: String?, type: String?): List<ArtObjectDomain> {
        val response = rijksmMuseumApi.getCollections(
            page = page,
            query = query,
            type = type
        )
        return response.successOrThrow()
            .artObjects
            .orEmpty()
            .map { it.toDomain() }
    }

    override suspend fun getArtById(id: String): ArtObjectDomain {
        return rijksmMuseumApi.getObjectDetails(id)
            .successOrThrow()
            .artObject
            ?.toDomain()!!
    }
}