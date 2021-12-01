package tgo1014.listofbeers.repositories

import retrofit2.Response
import tgo1014.listofbeers.models.Beer
import tgo1014.listofbeers.network.PunkApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BeersRepository @Inject constructor(private val punkApi: PunkApi) {

    suspend fun getBeers(page: Int): List<Beer> {
        return punkApi.getBeers(page).successOrThrow()
    }

    private fun <T> Response<T>.successOrThrow(): T {
        if (isSuccessful && body() != null) {
            return body()!!
        }
        throw Exception(this.errorBody()?.string())
    }
}