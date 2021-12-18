package tgo1014.beerbox.interactors

import tgo1014.beerbox.extensions.useCaseFlow
import tgo1014.beerbox.repositories.BeersRepository
import javax.inject.Inject

class GetBeersInteractor @Inject constructor(
    private val beersRepository: BeersRepository,
) {
    operator fun invoke(page: Int, search: String? = null, yeast: String? = null) = useCaseFlow {
        beersRepository.getBeers(page, search, yeast)
    }
}