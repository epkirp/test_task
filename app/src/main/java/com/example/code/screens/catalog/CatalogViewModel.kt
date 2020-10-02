package com.example.code.screens.catalog

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.code.R
import com.example.code.base.utils.Event
import com.example.code.utils.data.persistence.entities.ProductEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CatalogViewModel @Inject constructor(repository: CatalogContract.Repository) :
    CatalogContract.ViewModel(repository) {
    override val products = MutableLiveData<ArrayList<ProductEntity>>()
    override val navigateToProductScreen = MutableLiveData<Event<Unit>>()
    override val message = MutableLiveData<Event<Int>>()
    override val productsAmount = MutableLiveData<Int>()

    override fun onStart() {
        launch(IO) {
            repository.loadProducts()
        }
    }

    override fun onProductClick(product: ProductEntity) {
        launch {
            withContext(IO) {
                repository.updateSelectedProductInLocal(product)
            }
            navigateToProductScreen.value = Event(Unit)
        }
    }

    override fun onProductFavoriteClick(product: ProductEntity) {
        launch {
            product.isFavorite = !product.isFavorite
            val products = products.value
            if (product.isFavorite) {
                withContext(IO) {
                    repository.addFavoriteProductToDatabase(product)
                }
                message.value = Event(R.string.item_added_to_favorites)
            } else {
                withContext(IO) {
                    repository.removeFavoriteProductFromDatabase(product)
                }
            }
            this@CatalogViewModel.products.value = products
        }

    }

    override fun onProductsLoaded(products: ArrayList<ProductEntity>) {
        launch {
            val favoriteProducts = withContext(IO) {
                repository.getFavoriteProductsFromDatabase()
            }
            products.forEach {
                val foundProduct = favoriteProducts.find { favoriteProduct ->
                    favoriteProduct.id == it.id
                }
                if (foundProduct != null) {
                    it.isFavorite = true
                    return@forEach
                }
            }
            this@CatalogViewModel.products.value = products
            this@CatalogViewModel.productsAmount.value = products.size
        }
    }


override fun onProductsLoadError() {
    launch {
        message.value = Event(R.string.something_went_wrong)
    }
}


}