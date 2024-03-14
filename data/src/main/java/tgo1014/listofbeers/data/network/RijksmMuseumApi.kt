package tgo1014.listofbeers.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import tgo1014.listofbeers.data.models.GetCollectionsResponse

interface RijksmMuseumApi {

    @GET("collection")
    suspend fun getCollections(
        @Query("p") page: Int,
        @Query("q") query: String? = null,
        @Query("type") type: String?,
        @Query("ps") resultPerPage: Int = 100,
        @Query("imgonly") onlyWithImage: Boolean = true,
        @Query("toppieces") onlyTopPieces: Boolean = true,
        //@Query("s") sortBy: String = "relevance",
        @Query("key") key: String = "yvmj95Jo", // TODO move to a interceptor
    ): Response<GetCollectionsResponse>

}