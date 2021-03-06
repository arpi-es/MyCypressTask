package com.example.mycypresstask.data.remote


import com.example.mycypresstask.model.AlbumsItem
import com.example.mycypresstask.model.PhotosItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService{

    @GET("/albums")
    suspend fun getAlbums(@Query("userid") userId: Int) : Response<List<AlbumsItem>>

    @GET("/photos")
    suspend fun getPhotosByAlbumId(@Query("albumId") albumId: Int) : Response<List<PhotosItem>>


}
