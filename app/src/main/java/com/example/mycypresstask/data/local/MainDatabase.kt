package com.example.mycypresstask.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mycypresstask.model.AlbumsItem
import com.example.mycypresstask.model.PhotosItem

@Database(entities = [AlbumsItem::class, PhotosItem::class], version = 1, exportSchema = false )
abstract class MainDatabase : RoomDatabase() {
    abstract fun mainDatabaseDao(): MainDatabaseDao
}

