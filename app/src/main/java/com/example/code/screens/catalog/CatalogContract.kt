package com.example.code.screens.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.code.base.BaseActivity
import com.example.code.base.BaseRepository
import com.example.code.base.BaseViewModel
import com.example.code.base.utils.Event
import com.example.code.utils.data.persistence.entities.ProductEntity


interface CatalogContract {
    abstract class  View<VM: ViewModel>(layoutId: Int,viewModelClass: Class<VM>): BaseActivity<VM>(layoutId, viewModelClass) {
        protected abstract fun observeProducts(products: ArrayList<ProductEntity>)
        protected abstract fun observeProductsAmount(amount: Int)
        protected abstract fun observeNavigateToProductScreen(event: Event<Any>)
        protected abstract fun observeMessage(event: Event<Int>)
    }

    abstract class ViewModel(protected val repository: Repository): BaseViewModel() {
        abstract val products: MutableLiveData<ArrayList<ProductEntity>>
        abstract val navigateToProductScreen: MutableLiveData<Event<Unit>>
        abstract val message: MutableLiveData<Event<Int>>
        abstract val productsAmount: MutableLiveData<Int>

        init {
            repository.attachViewModel(this)
        }

        override fun onCleared() {
            super.onCleared()
            repository.detachViewModel()
        }

        abstract fun onProductClick(product: ProductEntity)
        abstract fun onProductFavoriteClick(product: ProductEntity)
        abstract fun onProductsLoaded(products: ArrayList<ProductEntity>)
        abstract fun onProductsLoadError()


    }

    abstract class Repository: BaseRepository<ViewModel>() {
        abstract suspend fun loadProducts()
        abstract suspend fun addFavoriteProductToDatabase(product: ProductEntity)
        abstract suspend fun removeFavoriteProductFromDatabase(product: ProductEntity)
        abstract suspend fun getFavoriteProductsFromDatabase(): ArrayList<ProductEntity>
        abstract suspend fun updateSelectedProductInLocal(product: ProductEntity)
    }
}