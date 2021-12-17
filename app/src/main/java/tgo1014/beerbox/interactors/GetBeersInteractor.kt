package tgo1014.beerbox.interactors

import tgo1014.beerbox.extensions.useCaseFlow
import tgo1014.beerbox.repositories.BeersRepository
import java.util.Date
import javax.inject.Inject

class GetBeersInteractor @Inject constructor(
    private val beersRepository: BeersRepository,
) {
    operator fun invoke(page: Int, after: Date? = null, before: Date? = null) = useCaseFlow {
        beersRepository.getBeers(page, after, before)
    }
}