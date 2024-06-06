package di

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.ResponseConverterFactory
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import network.RijksmMuseumApi
import network.createRijksmMuseumApi
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import repositories.ArtRepositoryImpl
import screens.home.HomeViewModel
import tgo1014.listofarts.domain.CoroutineProvider
import tgo1014.listofarts.domain.repositories.ArtRepository
import tgo1014.listofarts.domain.usecases.GetArtObjectsUseCase

fun commonModule(enableNetworkLogs: Boolean) = module {
    singleOf(::provideJson)
    singleOf(::provideKtorfit)
    singleOf(::provideHttpClient)
    singleOf(::provideCoroutineProvider)
    singleOf(::provideApi)
    singleOf(::ArtRepositoryImpl).bind<ArtRepository>()
    single { "yvmj95Jo" }
    factoryOf(::GetArtObjectsUseCase)
    viewModelOf(::HomeViewModel)
}

private fun provideApi(ktorfit: Ktorfit): RijksmMuseumApi = ktorfit.createRijksmMuseumApi()

private fun provideJson(): Json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

private fun provideCoroutineProvider(): CoroutineProvider = object : CoroutineProvider {
    override val main = Dispatchers.Main
    override val io = Dispatchers.Default
}

private fun provideHttpClient(httpClientEngine: HttpClientEngine, json: Json) = HttpClient(
    engine = httpClientEngine
) {
    install(ContentNegotiation) {
        json(json)
    }
    install(Logging) {
        logger = Logger.SIMPLE
        level = LogLevel.ALL
    }
    defaultRequest {
        this.url {
            this.parameters.append("key", "yvmj95Jo")
        }

    }
}

private fun provideKtorfit(httpClient: HttpClient): Ktorfit {
    return Ktorfit.Builder()
        .httpClient(httpClient)
        .converterFactories(ResponseConverterFactory())
        .baseUrl("https://www.rijksmuseum.nl/api/en/")
        .build()
}

fun appModule() = listOf(commonModule(true), platformModule())
