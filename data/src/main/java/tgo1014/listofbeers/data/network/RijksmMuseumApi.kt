package tgo1014.listofbeers.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import tgo1014.listofbeers.data.models.museum.GetCollectionsResponse

interface RijksmMuseumApi {

    @GET("collection")
    suspend fun getCollections(
        @Query("p") page: Int,
        @Query("q") query: String? = null,
        @Query("key") key: String = "yvmj95Jo",
    ): Response<GetCollectionsResponse>

}