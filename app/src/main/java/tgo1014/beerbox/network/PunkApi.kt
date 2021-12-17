package tgo1014.beerbox.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import tgo1014.beerbox.models.Beer

interface PunkApi {
    @GET("beers")
    suspend fun getBeers(
        @Query("page") page: Int? = null,
        @Query("brewed_before") brewedBefore: String? = null,
        @Query("brewed_after") brewedAfter: String? = null,
    ): Response<List<Beer>>
}