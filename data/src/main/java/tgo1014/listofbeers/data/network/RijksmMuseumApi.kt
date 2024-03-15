package tgo1014.listofbeers.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import tgo1014.listofbeers.data.models.GetCollectionsResponse
import tgo1014.listofbeers.data.models.GetObjectDetailResponse

/**
 * Documentation details: [Object metadata APIs](https://data.rijksmuseum.nl/object-metadata/api/)
 */
interface RijksmMuseumApi {

    @GET("collection")
    suspend fun getCollections(
        @Query("p") page: Int,
        @Query("q") query: String? = null,
        @Query("type") type: String?,
        @Query("ps") resultPerPage: Int = 100,
        @Query("imgonly") onlyWithImage: Boolean = true,
        @Query("toppieces") onlyTopPieces: Boolean = true,
    ): Response<GetCollectionsResponse>

    @GET("collection/{id}")
    suspend fun getObjectDetails(
        @Path("id") id: String,
    ): Response<GetObjectDetailResponse>
}