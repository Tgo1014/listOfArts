package tgo1014.listofbeers.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import tgo1014.listofbeers.data.network.PunkApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun providePunkApi(retrofit: Retrofit) = retrofit.create<PunkApi>()

}