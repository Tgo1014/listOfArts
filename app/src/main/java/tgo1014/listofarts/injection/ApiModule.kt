package tgo1014.listofarts.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import tgo1014.listofarts.data.network.RijksmMuseumApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideRijksmMuseumApi(retrofit: Retrofit) = retrofit.create<RijksmMuseumApi>()

}