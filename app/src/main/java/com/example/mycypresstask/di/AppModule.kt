package com.example.mycypresstask.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent

private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

@Module
@InstallIn(ActivityComponent::class)
object AppModule {

//    @Singleton
//    @Provides
//    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .addConverterFactory(GsonConverterFactory.create(gson))
//        .build()
//
//    @Provides
//    fun provideGson(): Gson = GsonBuilder().create()
//
//    @Provides
//    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
//

//    @Singleton
//    @Provides
//    fun provideApiService(apiService: ApiService) = CryptoRemoteDataSource(cryptoService)

}