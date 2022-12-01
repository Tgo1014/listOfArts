package tgo1014.listofbeers.data.usecases

import tgo1014.listofbeers.domain.usecases.GetBeersUseCase
import tgo1014.listofbeers.domain.repositories.BeersRepository
import javax.inject.Inject

class GetBeersUseCaseImpl @Inject constructor(
    private val beersRepository: BeersRepository,
) : GetBeersUseCase {
    override suspend operator fun invoke(
        page: Int,
        search: String?,
        yeast: String?,
    ) = runCatching {
        beersRepository.getBeers(page, search, yeast)
    }
}