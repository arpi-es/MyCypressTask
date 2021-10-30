package com.example.mycypresstask.data.remote

import com.example.mycypresstask.model.AlbumsItem
import com.example.mycypresstask.model.PhotosItem
import com.example.mycypresstask.utils.Result
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import javax.inject.Inject


class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

//      fun getAlbums(userId: Int) = apiService.getAlbums(userId)
//      fun getPhotos(albumId: Int) = apiService.getPhotosByAlbumId(albumId)

    suspend fun fetchAlbums(userId: Int):  Result<List<AlbumsItem>> {
        return getResponse(
            request = { apiService.getAlbums(userId) },
            defaultErrorMessage = "Error fetching Albums list"
        )
    }


    suspend fun fetchPhotos(albumId: Int):  Result<List<PhotosItem>> {
        return getResponse(
            request = { apiService.getPhotosByAlbumId(albumId) },
            defaultErrorMessage = "Error fetching Photos"
        )
    }


    private suspend fun <T> getResponse(request: suspend () -> Response<T>, defaultErrorMessage: String): Result<T> {
        return try {
            println("I'm working in thread ${Thread.currentThread().name}")
            val result = request.invoke()
            if (result.isSuccessful) {
                return Result.success(result.body())
            } else {
//              val errorResponse = ErrorUtils.parseError(result, retrofit)
//               Result.error(errorResponse?.status_message ?: defaultErrorMessage, errorResponse)
//              TODO Handle ERROR MSG
                Result.error("Error", null)

            }
        } catch (e: Throwable) {
            Result.error("Unknown Error", null)
        }
    }
}