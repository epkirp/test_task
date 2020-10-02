package com.example.code.screens.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.code.base.BaseActivity
import com.example.code.base.BaseRepository
import com.example.code.base.BaseViewModel
import com.example.code.base.utils.Event
import com.example.code.utils.data.persistence.entities.ProductEntity

interface ProductContract {
    abstract class  View<VM: ViewModel>(layoutId: Int,viewModelClass: Class<VM>): BaseActivity<VM>(layoutId, viewModelClass) {
        protected abstract fun observeMessage(event: Event<Int>)
        protected abstract fun observeAddedToFavorite(added: Boolean)
        protected abstract fun observeProduct(product: ProductEntity)
        protected abstract fun observeNavigateToPreviousScreen(event: Event<Unit>)
    }

    abstract class ViewModel(protected val repository: Repository): BaseViewModel() {
        abstract val message: MutableLiveData<Event<Int>>
        abstract val navigateToPreviousScreen: MutableLiveData<Event<Unit>>
        abstract val addedToFavorite: MutableLiveData<Boolean>
        abstract val product: MutableLiveData<ProductEntity>

        abstract fun onBtnFavoriteClick()
        abstract fun onBtnAddToCartClick()
        abstract fun onBtnBackClick()

        init {
            repository.attachViewModel(this)
        }

        override fun onCleared() {
            super.onCleared()
            repository.detachViewModel()
        }

    }

    abstract class Repository: BaseRepository<ViewModel>() {
        abstract suspend fun addFavoriteProductToDatabase(product: ProductEntity)
        abstract suspend fun removeFavoriteProductFromDatabase(product: ProductEntity)
        abstract suspend fun getSelectedProductFromLocal(): ProductEntity

    }
}