package tgo1014.listofbeers.data.repositories

import retrofit2.Response
import tgo1014.listofbeers.data.network.PunkApi
import tgo1014.listofbeers.domain.models.BeerDomain
import tgo1014.listofbeers.domain.repositories.BeersRepository
import javax.inject.Inject


class BeersRepositoryImpl @Inject constructor(
    private val punkApi: PunkApi
) : BeersRepository {

    override suspend fun getBeers(
        page: Int,
        search: String?,
        yeast: String?
    ): List<BeerDomain> {
        val query = if (search.isNullOrBlank()) {
            null
        } else {
            search.replace(" ", "_")
        }
        val response = punkApi.getBeers(
            page = page,
            beerName = query,
            yeast = yeast
        )
        return TODO() //response.successOrThrow()
    }

    private fun <T> Response<T>.successOrThrow(): T {
        if (isSuccessful && body() != null) {
            return body()!!
        }
        throw Exception(this.errorBody()?.string())
    }
}