package com.example.mycypresstask.data.repository

import com.example.mycypresstask.data.remote.ApiService

class MainRepository  (private val  apiService: ApiService ) {

    private val USER_ID = 1

    suspend fun getAlbums() = apiService.getAlbums(USER_ID)

    suspend fun getPhotos(albumId : Int) = apiService.getPhotosByAlbumId(albumId)
}
