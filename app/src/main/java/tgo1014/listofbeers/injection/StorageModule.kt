package tgo1014.listofbeers.injection

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tgo1014.listofbeers.repositories.BeersRepository
import tgo1014.listofbeers.repositories.BeersRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModule {

    @Singleton
    @Binds
    abstract fun providesBeersRepository(beersRepositoryImpl: BeersRepositoryImpl): BeersRepository

}