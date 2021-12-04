package tgo1014.listofbeers.interactors

import tgo1014.listofbeers.extensions.useCaseFlow
import tgo1014.listofbeers.repositories.BeersRepository
import java.util.Date
import javax.inject.Inject

class GetBeersInteractor @Inject constructor(
    private val beersRepository: BeersRepository,
) {
    operator fun invoke(page: Int, after: Date? = null, before: Date? = null) = useCaseFlow {
        beersRepository.getBeers(page, after, before)
    }
}