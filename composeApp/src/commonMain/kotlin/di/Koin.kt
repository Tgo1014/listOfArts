package di

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.ResponseConverterFactory
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import network.RijksmMuseumApi
import network.createRijksmMuseumApi
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
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
    singleOf(::provideCoroutineProvider)
    singleOf(::provideApi)
    singleOf(::ArtRepositoryImpl).bind<ArtRepository>()
    single { "yvmj95Jo" }
    factoryOf(::GetArtObjectsUseCase)
    viewModelOf(::HomeViewModel)
}

private fun provideApi(ktorfit: Ktorfit): RijksmMuseumApi {
    return ktorfit.createRijksmMuseumApi()
}

private fun provideJson(): Json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

// TODO make a version platform based that has IO provided
private fun provideCoroutineProvider(): CoroutineProvider = object : CoroutineProvider {
    override val main = Dispatchers.Main
    override val default = Dispatchers.Default
    override val io = Dispatchers.Default
}

private fun provideKtorfit(json: Json): Ktorfit {
    return Ktorfit.Builder()
        .httpClient {
            install(ContentNegotiation) {
                json(json)
            }
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
            defaultRequest {
                url { parameters.append("key", "yvmj95Jo") }
            }
        }
        .converterFactories(ResponseConverterFactory())
        .baseUrl("https://www.rijksmuseum.nl/api/en/")
        .build()
}

fun appModule() = listOf(commonModule(true), platformModule())
