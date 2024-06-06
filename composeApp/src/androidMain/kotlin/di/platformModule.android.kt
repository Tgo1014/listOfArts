package di

import io.ktor.client.engine.HttpClientEngine
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule() = module {
   // single<HttpClientEngine> { Android.create() }
}