package com.example.code.utils.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.code.utils.data.persistence.dao.ProductDao
import com.example.code.utils.data.persistence.entities.ProductEntity

@Database(entities = [ProductEntity::class], version = 1,exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}