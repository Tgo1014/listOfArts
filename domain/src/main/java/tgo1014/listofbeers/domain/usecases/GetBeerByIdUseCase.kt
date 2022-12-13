package tgo1014.listofbeers.domain.usecases

import kotlinx.coroutines.withContext
import tgo1014.listofbeers.domain.CoroutineProvider
import tgo1014.listofbeers.domain.repositories.BeersRepository

class GetBeerByIdUseCase constructor(
    private val beersRepository: BeersRepository,
    private val coroutineProvider: CoroutineProvider,
) {

    suspend operator fun invoke(id: Int) = withContext(coroutineProvider.io) {
        runCatching { beersRepository.getBeerById(id) }
    }

}