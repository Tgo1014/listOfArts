package tgo1014.listofbeers.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import tgo1014.listofbeers.models.Beer

interface PunkApi {
    @GET("beers")
    suspend fun getBeers(
        @Query("page") page: Int? = null,
    ): Response<List<Beer>>
}