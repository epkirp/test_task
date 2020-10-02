package com.example.code.screens.catalog

import kotlinx.coroutines.launch
import com.example.code.utils.api.ApiService
import com.example.code.utils.data.LocalDataManager
import com.example.code.utils.data.persistence.dao.ProductDao
import com.example.code.utils.data.persistence.entities.ProductEntity
import javax.inject.Inject

class CatalogRepository @Inject constructor(private val apiService: ApiService, private val productDao: ProductDao): CatalogContract.Repository() {

    override suspend fun loadProducts() {
            try {
                val response = apiService.getProducts()
                if (response.isSuccessful) {
                    val data = response.body()
                    if(data != null) {
                        val products = ArrayList<ProductEntity>()
                        data.forEach {
                            products.add(ProductEntity(it.id,it.title,it.image,it.price,it.new))
                        }
                        viewModel?.onProductsLoaded(products)
                    }
                } else {
                    viewModel?.onProductsLoadError()
                }
            } catch (e: Exception) {
                viewModel?.onProductsLoadError()
            }

    }

    override suspend fun addFavoriteProductToDatabase(product: ProductEntity) {
            productDao.insertProduct(product)
    }

    override suspend fun removeFavoriteProductFromDatabase(product: ProductEntity)  {
            productDao.removeProduct(product)
    }

    override suspend fun getFavoriteProductsFromDatabase(): ArrayList<ProductEntity>{
        return ArrayList(productDao.getAllProducts())
    }

    override suspend fun updateSelectedProductInLocal(product: ProductEntity) {
        LocalDataManager.selectedProduct = product
    }

}