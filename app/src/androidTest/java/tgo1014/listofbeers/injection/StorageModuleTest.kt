package tgo1014.listofbeers.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import tgo1014.listofbeers.domain.repositories.BeersRepository
import tgo1014.listofbeers.fakes.FakeBeerRepository
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [StorageModule::class]
)
object StorageModuleTest {

    @Singleton
    @Provides
    fun providesFakeBeerRepository(): BeersRepository = FakeBeerRepository()

}