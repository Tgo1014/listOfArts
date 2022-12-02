package tgo1014.listofbeers.presentation.fakes

import tgo1014.listofbeers.domain.models.BeerDomain
import tgo1014.listofbeers.domain.usecases.GetBeersUseCase

class FakeGetBeersUseCase(
    var beersToReturn: List<BeerDomain> = emptyList(),
    var throwException: Boolean = false,
) : GetBeersUseCase {

    var page: Int? = null
    var search: String? = null
    var yeast: String? = null

    override suspend fun invoke(
        page: Int,
        search: String?,
        yeast: String?
    ): Result<List<BeerDomain>> {
        this.page = page
        this.search = search
        this.yeast = yeast
        return if (throwException) {
            Result.failure(Exception())
        } else {
            Result.success(beersToReturn)
        }
    }

}