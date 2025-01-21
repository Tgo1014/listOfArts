package repositories

import extensions.successOrThrow
import models.GetCollectionsResponse
import models.mappers.toDomain
import network.RijksmMuseumApi
import tgo1014.listofarts.domain.models.ArtObjectDomain
import tgo1014.listofarts.domain.repositories.ArtRepository

class ArtRepositoryImpl(
    private val rijksmMuseumApi: RijksmMuseumApi,
    private val key: String
) : ArtRepository {

    override suspend fun getArt(page: Int, query: String?, type: String?): List<ArtObjectDomain> {
        val response = rijksmMuseumApi.getCollections(
            page = page,
            query = query,
            type = type,
            key = key
        )
        return response
            .successOrThrow()
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