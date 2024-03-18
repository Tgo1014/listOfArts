package tgo1014.listofarts.injection

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tgo1014.listofarts.data.repositories.ArtRepositoryImpl
import tgo1014.listofarts.domain.repositories.ArtRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModule {

    @Singleton
    @Binds
    abstract fun providesArtsRepository(artRepositoryImpl: ArtRepositoryImpl): ArtRepository

}