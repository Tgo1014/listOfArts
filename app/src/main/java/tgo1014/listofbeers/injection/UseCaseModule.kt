package tgo1014.listofbeers.injection

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tgo1014.listofbeers.domain.usecases.GetBeerByIdUseCase
import tgo1014.listofbeers.domain.usecases.GetBeerByIdUseCaseImpl
import tgo1014.listofbeers.domain.usecases.GetBeersUseCase
import tgo1014.listofbeers.domain.usecases.GetBeersUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindGetBeersUseCase(useCase: GetBeersUseCaseImpl): GetBeersUseCase

    @Binds
    abstract fun bindGetBeerByIdUseCase(useCase: GetBeerByIdUseCaseImpl): GetBeerByIdUseCase

}