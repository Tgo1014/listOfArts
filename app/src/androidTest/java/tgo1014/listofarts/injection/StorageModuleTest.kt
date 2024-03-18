package tgo1014.listofarts.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import tgo1014.listofarts.domain.repositories.ArtRepository
import tgo1014.listofarts.fakes.FakeArtRepository
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [StorageModule::class]
)
object StorageModuleTest {

    @Singleton
    @Provides
    fun providesFakeArtRepository(): ArtRepository = FakeArtRepository()

}