package tgo1014.listofbeers.data.usecases

import kotlinx.coroutines.withContext
import tgo1014.listofbeers.domain.CoroutineProvider
import tgo1014.listofbeers.domain.models.BeerDomain
import tgo1014.listofbeers.domain.repositories.BeersRepository
import tgo1014.listofbeers.domain.usecases.GetBeersUseCase
import javax.inject.Inject

class GetBeersUseCaseImpl @Inject constructor(
    private val beersRepository: BeersRepository,
    private val coroutineProvider: CoroutineProvider,
) : GetBeersUseCase {

    override suspend operator fun invoke(
        page: Int,
        search: String?,
        yeast: String?,
    ): Result<List<BeerDomain>> = withContext(coroutineProvider.io) {
        runCatching {
            val query = if (search.isNullOrBlank()) {
                null
            } else {
                search.replace(" ", "_")
            }
            beersRepository.getBeers(page = page, search = query, yeast = yeast)
        }
    }

}