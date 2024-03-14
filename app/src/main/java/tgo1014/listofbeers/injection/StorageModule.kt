package tgo1014.listofbeers.injection

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tgo1014.listofbeers.domain.repositories.ArtRepository
import tgo1014.listofbeers.data.repositories.ArtRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModule {

    @Singleton
    @Binds
    abstract fun providesBeersRepository(beersRepositoryImpl: ArtRepositoryImpl): ArtRepository

}