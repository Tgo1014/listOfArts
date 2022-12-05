package tgo1014.listofbeers.injection

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import tgo1014.listofbeers.domain.repositories.BeersRepository
import tgo1014.listofbeers.data.repositories.BeersRepositoryImpl
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [StorageModule::class]
)
abstract class StorageModuleTest {

    @Singleton
    @Binds
    abstract fun providesBeersRepository(beersRepositoryImpl: BeersRepositoryImpl): BeersRepository

}