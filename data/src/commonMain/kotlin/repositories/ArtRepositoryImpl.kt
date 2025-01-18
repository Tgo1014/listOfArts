package repositories

import extensions.successOrThrow
import models.GetCollectionsResponse
import models.mappers.toDomain
import network.RijksmMuseumApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tgo1014.listofarts.domain.models.ArtObjectDomain
import tgo1014.listofarts.domain.repositories.ArtRepository

class ArtRepositoryImpl: ArtRepository, KoinComponent {

    private val rijksmMuseumApi: RijksmMuseumApi by inject()
    private val key: String by inject()

    override suspend fun getArt(page: Int, query: String?, type: String?): List<ArtObjectDomain> {
        val response = rijksmMuseumApi.getCollections(
            page = page,
            query = query,
            type = type,
            key = key
        )
        return response
            .successOrThrow<GetCollectionsResponse>()
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