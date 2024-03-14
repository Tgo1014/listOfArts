package tgo1014.listofbeers.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import mockwebserver3.MockWebServer
import tgo1014.listofbeers.utils.getService
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ApiModule::class]
)
object ApiModuleTest {

    @Singleton
    @Provides
    fun provideMockWebServer() = MockWebServer()

    @Singleton
    @Provides
    fun providePunkApi(mockWebServer: MockWebServer): PunkApi {
        // Needs to be created on IO or throws NetworkOnMainThreadException
        // https://github.com/fknives/AndroidTest-ShowCase/issues/14#issuecomment-1017591435
        return runBlocking(Dispatchers.IO) { mockWebServer.getService() }
    }

}