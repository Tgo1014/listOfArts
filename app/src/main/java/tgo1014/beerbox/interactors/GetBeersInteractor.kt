package tgo1014.beerbox.interactors

import tgo1014.beerbox.models.Beer
import tgo1014.beerbox.repositories.BeersRepository
import timber.log.Timber
import javax.inject.Inject

class GetBeersInteractor @Inject constructor(
    private val beersRepository: BeersRepository,
) {
    suspend operator fun invoke(
        page: Int,
        search: String? = null,
        yeast: String? = null
    ): Result<List<Beer>> {
        return try {
            Result.success(beersRepository.getBeers(page, search, yeast))
        } catch (e: Exception) {
            Timber.w(e)
            Result.failure(e)
        }
    }
}