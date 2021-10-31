package com.example.mycypresstask.di

import android.content.Context
import androidx.room.Room
import com.example.mycypresstask.data.local.MainDatabase
import com.example.mycypresstask.data.local.MainDatabaseDao
import com.example.mycypresstask.data.remote.ApiService
import com.example.mycypresstask.data.remote.RemoteDataSource
import com.example.mycypresstask.data.repository.MainRepository
import com.example.mycypresstask.ui.adapters.AlbumsAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com"
    private const val DATABASE = "maindb"
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()


    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)


    @Provides
    fun provideRemoteDataSource(apiService: ApiService) = RemoteDataSource(apiService)


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): MainDatabase {
        return Room.databaseBuilder(
            appContext,
            MainDatabase::class.java,
            DATABASE
        ).build()
    }



    @Singleton
    @Provides
    fun provideMainDatabaseDao(db: MainDatabase) = db.mainDatabaseDao()


    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: RemoteDataSource, localDataSource: MainDatabaseDao) =
                           MainRepository(remoteDataSource, localDataSource)



    @Singleton
    @Provides
    fun provideAlbumAdapter() = AlbumsAdapter()

}