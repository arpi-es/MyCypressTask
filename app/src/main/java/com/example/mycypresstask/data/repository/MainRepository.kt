package com.example.mycypresstask.data.repository

import android.util.Log
import com.example.mycypresstask.data.local.MainDatabaseDao
import com.example.mycypresstask.data.remote.RemoteDataSource
import com.example.mycypresstask.model.AlbumsItem
import com.example.mycypresstask.model.PhotosItem
import com.example.mycypresstask.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: MainDatabaseDao
) {

    private val USERID = 1

    suspend fun fetchAllAlbums(): Flow<Result<List<AlbumsItem>>> {
        return flow {
            /* GetTheCachedData */
            emit(fetchAllAlbumsCached())

            /* Get Data From Api */
            val result = remoteDataSource.fetchAlbums(USERID)

            /* Cache to database if response is successful */
            if (result.status == Result.Status.SUCCESS) {
                result.data?.let { it ->
                    localDataSource.insertAllAlbum(it)
//                    Log.i("MYTAG", "Inserted ${it.size} Albums from API in DB...")
                }
            }

            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    private fun fetchAllAlbumsCached(): Result<List<AlbumsItem>> =
        cachedAlbums().let { Result.success(it) }


    suspend fun fetchAllPhotos(albumId: Int): Flow<Result<List<PhotosItem>>> {
        return flow {

            emit(fetchAllPhotosCached(albumId))

            val result = remoteDataSource.fetchPhotos(albumId)
            /* Cache to database if response is successful */
            if (result.status == Result.Status.SUCCESS) {
                result.data?.let { it ->
                    localDataSource.insertAllPhoto(it)
//                    Log.i("MYTAG", "Inserted ${it.size}  PhotosItem  from API in DB...")
                }
            }
            emit(result)
        }.flowOn(Dispatchers.IO)

    }

    private fun fetchAllPhotosCached(albumId: Int): Result<List<PhotosItem>> =
        cachedPhotosByID(albumId).let { Result.success(it) }



    /* for better performance first get cached data */
    suspend fun getCachedAlbums(): Flow<Result<List<AlbumsItem>>> {
        return flow {
            emit(fetchAllAlbumsCached())
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getCachedPhotos(albumId: Int): Flow<List<PhotosItem>> {
        return flow {
            emit(cachedPhotosByID(albumId))
        }.flowOn(Dispatchers.IO)
    }



    private fun cachedAlbums() = localDataSource.getAllAlbum()

    private fun cachedPhotosByID(albumId: Int) = localDataSource.getPhotosByID(albumId)

}
