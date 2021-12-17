package tgo1014.beerbox.repositories

import retrofit2.Response
import tgo1014.beerbox.models.Beer
import tgo1014.beerbox.network.PunkApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BeersRepository @Inject constructor(private val punkApi: PunkApi) {

    suspend fun getBeers(page: Int, search: String? = null): List<Beer> {
        val query = if (search.isNullOrBlank()) {
            null
        } else {
            search.replace(" ", "_")
        }
        val response = punkApi.getBeers(
            page = page,
            beerName = query,
        )
        return response.successOrThrow()
    }

    private fun <T> Response<T>.successOrThrow(): T {
        if (isSuccessful && body() != null) {
            return body()!!
        }
        throw Exception(this.errorBody()?.string())
    }
}