package di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.java.Java
import org.koin.dsl.module

actual fun platformModule() = module {
    single<HttpClientEngine> { Java.create() }
}