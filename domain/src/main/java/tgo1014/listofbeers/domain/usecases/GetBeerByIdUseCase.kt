package tgo1014.listofbeers.domain.usecases

import tgo1014.listofbeers.domain.models.BeerDomain

interface GetBeerByIdUseCase {
    suspend operator fun invoke(id: Int): Result<BeerDomain>
}