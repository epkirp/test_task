package com.example.code.utils.data.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "products")

data class ProductEntity (
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Long = 0,
    @ColumnInfo(name = "title")
    var title: String = "",
    @ColumnInfo(name = "image")
    var image: String = "",
    @ColumnInfo(name = "price")
    var price: String = "",
    @ColumnInfo(name = "new")
    var new: Boolean = false,
    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)

