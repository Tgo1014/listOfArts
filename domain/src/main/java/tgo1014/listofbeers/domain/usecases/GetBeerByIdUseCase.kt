package tgo1014.listofbeers.domain.usecases

import kotlinx.coroutines.withContext
import tgo1014.listofbeers.domain.CoroutineProvider
import tgo1014.listofbeers.domain.repositories.BeersRepository
import javax.inject.Inject

class GetBeerByIdUseCase @Inject constructor(
    private val beersRepository: BeersRepository,
    private val coroutineProvider: CoroutineProvider,
) {

    suspend operator fun invoke(id: Int) = runCatching {
        withContext(coroutineProvider.io) {
            beersRepository.getBeerById(id)
        }
    }

}
