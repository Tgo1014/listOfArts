package tgo1014.listofbeers.domain.usecases

import kotlinx.coroutines.withContext
import tgo1014.listofbeers.domain.CoroutineProvider
import tgo1014.listofbeers.domain.repositories.ArtRepository
import javax.inject.Inject

class GetArtObjectByIdUseCase @Inject constructor(
    private val artRepository: ArtRepository,
    private val coroutineProvider: CoroutineProvider,
) {

    suspend operator fun invoke(id: String) = runCatching {
        withContext(coroutineProvider.io) {
            artRepository.getArtById(id.replace("en-", ""))
        }
    }

}
