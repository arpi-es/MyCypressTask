package com.example.mycypresstask.data.remote


import com.example.mycypresstask.model.AlbumsItem
import com.example.mycypresstask.model.PhotosItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService{

    @GET("albums")
    suspend fun getAlbums(@Path("userid") userId : Int) : Call<List<AlbumsItem>>


    @GET("photos")
    suspend fun getPhotosByAlbumId(@Path("albumId") albumId : Int) : Call<List<PhotosItem>>

}
