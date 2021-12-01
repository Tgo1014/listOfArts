package tgo1014.listofbeers.interactors

import tgo1014.listofbeers.extensions.useCaseFlow
import tgo1014.listofbeers.repositories.BeersRepository
import javax.inject.Inject

class GetBeersInteractor @Inject constructor(private val beersRepository: BeersRepository) {
    operator fun invoke(page: Int) = useCaseFlow {
        beersRepository.getBeers(page)
    }
}