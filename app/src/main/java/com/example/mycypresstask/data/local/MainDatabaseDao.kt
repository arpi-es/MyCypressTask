package com.example.mycypresstask.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mycypresstask.model.AlbumsItem
import com.example.mycypresstask.model.PhotosItem


@Dao
interface MainDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllAlbum(albums: List<AlbumsItem>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllPhoto(photos: List<PhotosItem>)

    @Query("SELECT * FROM tblAlbum")
    fun getAllAlbum(): List<AlbumsItem>

    @Query("SELECT * FROM tblPhoto WHERE albumId = :albumId")
    fun getPhotosByID(albumId : Int): List<PhotosItem>


}