package tgo1014.beerbox.interactors

import tgo1014.beerbox.repositories.BeersRepository
import javax.inject.Inject

class GetBeersInteractor @Inject constructor(
    private val beersRepository: BeersRepository,
) {
    suspend operator fun invoke(
        page: Int,
        search: String? = null,
        yeast: String? = null
    ) = runCatching { beersRepository.getBeers(page, search, yeast) }
}