package tgo1014.listofarts.domain.usecases

import kotlinx.coroutines.withContext
import tgo1014.listofarts.domain.CoroutineProvider
import tgo1014.listofarts.domain.repositories.ArtRepository

class GetArtObjectByIdUseCase constructor(
    private val artRepository: ArtRepository,
    private val coroutineProvider: CoroutineProvider,
) {

    suspend operator fun invoke(id: String) = runCatching {
        withContext(coroutineProvider.io) {
            artRepository.getArtById(id.replace("en-", ""))
        }
    }
}
