package tgo1014.listofbeers.domain.usecases

import tgo1014.listofbeers.domain.models.BeerDomain

interface GetBeersUseCase {
    suspend operator fun invoke(
        page: Int,
        search: String? = null,
        yeast: String? = null,
    ): Result<List<BeerDomain>>
}
