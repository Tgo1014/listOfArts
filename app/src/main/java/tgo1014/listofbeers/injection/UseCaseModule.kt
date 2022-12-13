package tgo1014.listofbeers.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tgo1014.listofbeers.domain.CoroutineProvider
import tgo1014.listofbeers.domain.repositories.BeersRepository
import tgo1014.listofbeers.domain.usecases.GetBeerByIdUseCase
import tgo1014.listofbeers.domain.usecases.GetBeerByIdUseCaseImpl
import tgo1014.listofbeers.domain.usecases.GetBeersUseCase
import tgo1014.listofbeers.domain.usecases.GetBeersUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun bindGetBeersUseCase(
        beersRepository: BeersRepository,
        coroutineProvider: CoroutineProvider,
    ): GetBeersUseCase = GetBeersUseCaseImpl(beersRepository, coroutineProvider)

    @Provides
    fun bindGetBeerByIdUseCase(
        beersRepository: BeersRepository,
        coroutineProvider: CoroutineProvider,
    ): GetBeerByIdUseCase = GetBeerByIdUseCaseImpl(beersRepository, coroutineProvider)

}