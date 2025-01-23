package tgo1014.listofarts.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import mockwebserver3.MockWebServer
import okhttp3.ExperimentalOkHttpApi
import tgo1014.listofarts.data.network.RijksmMuseumApi
import tgo1014.listofarts.utils.getService
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ApiModule::class]
)
@OptIn(ExperimentalOkHttpApi::class)
object ApiModuleTest {


    @Singleton
    @Provides
    fun provideMockWebServer() = MockWebServer()

    @Singleton
    @Provides
    fun provideApi(mockWebServer: MockWebServer): RijksmMuseumApi {
        // Needs to be created on IO or throws NetworkOnMainThreadException
        // https://github.com/fknives/AndroidTest-ShowCase/issues/14#issuecomment-1017591435
        return runBlocking(Dispatchers.IO) { mockWebServer.getService() }
    }

}