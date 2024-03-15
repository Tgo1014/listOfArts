package tgo1014.listofarts.utils

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import mockwebserver3.MockWebServer
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

inline fun <reified T> MockWebServer.getService(): T = Retrofit.Builder()
    .baseUrl(this.url("/"))
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .build()
    .create(T::class.java)