package tgo1014.listofbeers.presentation.fakes

import tgo1014.listofbeers.domain.models.BeerDomain
import tgo1014.listofbeers.domain.usecases.GetBeerByIdUseCase

class FakeGetBeerByIdUseCase(
    private var beerToReturn: BeerDomain,
    var throwException: Boolean = false,
) : GetBeerByIdUseCase {

    override suspend fun invoke(id: Int): Result<BeerDomain> {
        return if (throwException) {
            Result.failure(Exception())
        } else {
            Result.success(beerToReturn)
        }
    }

}