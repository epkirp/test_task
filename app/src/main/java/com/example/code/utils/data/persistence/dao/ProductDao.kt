package com.example.code.utils.data.persistence.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.code.utils.data.persistence.entities.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM products")
    suspend fun getAllProducts(): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)

    @Delete
    suspend fun removeProduct(product: ProductEntity)
}



