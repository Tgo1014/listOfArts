package tgo1014.listofbeers.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import tgo1014.listofbeers.domain.CoroutineProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providesCoroutineProvider(): CoroutineProvider {
        return object : CoroutineProvider {
            override val main = Dispatchers.Main
            override val io = Dispatchers.IO
        }
    }

}