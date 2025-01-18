package tgo1014.listofarts.domain.usecases

import kotlinx.coroutines.withContext
import tgo1014.listofarts.domain.CoroutineProvider
import tgo1014.listofarts.domain.models.ArtObjectDomain
import tgo1014.listofarts.domain.repositories.ArtRepository

class GetArtObjectsUseCase(
    private val coroutineProvider: CoroutineProvider,
    private val artRepository: ArtRepository,
) {
    suspend operator fun invoke(
        page: Int,
        query: String? = null,
        type: String? = null,
    ): Result<List<ArtObjectDomain>> = runCatching {
        val searchQuery = if (query.isNullOrBlank()) {
            null
        } else {
            query.replace(" ", "_")
        }
        withContext(coroutineProvider.io) {
            artRepository.getArt(page = page, query = searchQuery, type = type)
        }
    }.onFailure {
        println(it)
    }
}
