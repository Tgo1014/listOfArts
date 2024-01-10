package tgo1014.listofbeers.domain.usecases

import kotlinx.coroutines.withContext
import tgo1014.listofbeers.domain.CoroutineProvider
import tgo1014.listofbeers.domain.models.BeerDomain
import tgo1014.listofbeers.domain.repositories.BeersRepository
import javax.inject.Inject

class GetBeersUseCase @Inject constructor(
    private val beersRepository: BeersRepository,
    private val coroutineProvider: CoroutineProvider,
) {

    suspend operator fun invoke(
        page: Int,
        search: String? = null,
        yeast: String? = null,
    ): Result<List<BeerDomain>> = runCatching {
        val query = if (search.isNullOrBlank()) {
            null
        } else {
            search.replace(" ", "_")
        }
        withContext(coroutineProvider.io) {
            beersRepository.getBeers(page = page, search = query, yeast = yeast)
        }
    }
}
