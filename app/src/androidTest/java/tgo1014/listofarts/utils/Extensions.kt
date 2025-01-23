package tgo1014.listofarts.utils

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import mockwebserver3.MockWebServer
import okhttp3.ExperimentalOkHttpApi
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

@OptIn(ExperimentalOkHttpApi::class)
inline fun <reified T> MockWebServer.getService(): T = Retrofit.Builder()
    .baseUrl(this.url("/"))
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .build()
    .create(T::class.java)

fun SemanticsNodeInteractionsProvider.logAllComposableNodes() {
    this.onRoot().printToLog("TAG")
}