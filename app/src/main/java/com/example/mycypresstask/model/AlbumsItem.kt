package com.example.mycypresstask.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tblAlbum" )
data class AlbumsItem(
    @PrimaryKey
    val id: Int,
    val title: String,
    val userId: Int
)