package tgo1014.listofbeers

import com.squareup.moshi.Moshi
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object TestHelper {

    fun generateRetrofit(mockWebServer: MockWebServer): Retrofit = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
}

inline fun <reified T> MockWebServer.getService(): T = Retrofit.Builder()
    .baseUrl(this.url("/"))
    .addConverterFactory(MoshiConverterFactory.create())
    .build()
    .create(T::class.java)

inline fun <reified T> T.toJsonString(): String {
    val moshi = Moshi.Builder().build()
    return moshi.adapter(T::class.java).toJson(this)
}
