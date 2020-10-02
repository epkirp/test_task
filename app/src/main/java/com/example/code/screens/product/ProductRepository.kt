package com.example.code.screens.product

import com.example.code.utils.data.LocalDataManager
import com.example.code.utils.data.persistence.dao.ProductDao
import com.example.code.utils.data.persistence.entities.ProductEntity
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productDao:ProductDao): ProductContract.Repository() {
    override suspend fun addFavoriteProductToDatabase(product: ProductEntity) {
            productDao.insertProduct(product)
    }

    override suspend fun removeFavoriteProductFromDatabase(product: ProductEntity) {
            productDao.removeProduct(product)
    }

    override suspend fun getSelectedProductFromLocal(): ProductEntity {
        return LocalDataManager.selectedProduct!!
    }


}