package network

import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query
import models.GetCollectionsResponse
import models.GetObjectDetailResponse

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
        @Query("key") key: String,
    ): Response<GetCollectionsResponse>

    @GET("collection/{id}")
    suspend fun getObjectDetails(
        @Path("id") id: String,
    ): Response<GetObjectDetailResponse>
}