package tgo1014.listofbeers.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import tgo1014.listofbeers.data.models.BeerData

interface PunkApi {

    @GET("beers")
    suspend fun getBeers(
        @Query("page") page: Int? = null,
        @Query("beer_name") beerName: String? = null,
        @Query("yeast") yeast: String? = null,
    ): Response<List<BeerData>>

    @GET("beers/{id}")
    suspend fun getBeerById(@Path("id") id: Int): Response<BeerData>

}