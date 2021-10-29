package com.example.mycypresstask.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tblPhoto")
data class PhotosItem(
    val albumId: Int,
    @PrimaryKey
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)