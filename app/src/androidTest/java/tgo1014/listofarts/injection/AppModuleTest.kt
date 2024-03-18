package tgo1014.listofarts.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import tgo1014.listofarts.domain.CoroutineProvider
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
class AppModuleTest {

    @Singleton
    @Provides
    fun providesCoroutineProvider(): CoroutineProvider {
        val dispatcher = UnconfinedTestDispatcher()
        return object : CoroutineProvider {
            override val main = dispatcher
            override val io = dispatcher
        }
    }

}