package com.example.code.screens.product

import androidx.lifecycle.MutableLiveData
import com.example.code.R
import com.example.code.base.utils.Event
import com.example.code.utils.data.persistence.entities.ProductEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductViewModel @Inject constructor(repository: ProductContract.Repository) :
    ProductContract.ViewModel(repository) {
    override val message = MutableLiveData<Event<Int>>()
    override val navigateToPreviousScreen = MutableLiveData<Event<Unit>>()
    override val addedToFavorite = MutableLiveData<Boolean>()
    override val product = MutableLiveData<ProductEntity>()

    init {
        launch {
            val selectedProduct = withContext(IO) {
                repository.getSelectedProductFromLocal()
            }
            product.value = selectedProduct
        }
    }

    override fun onBtnFavoriteClick() {
        launch {
            val selectedProduct = withContext(IO) {
                repository.getSelectedProductFromLocal()
            }
            selectedProduct.isFavorite = !selectedProduct.isFavorite
            if (selectedProduct.isFavorite) {
                withContext(IO){
                    repository.addFavoriteProductToDatabase(selectedProduct)
                }
                message.value = Event(R.string.item_added_to_favorites)
                addedToFavorite.value = true
            } else {
                addedToFavorite.value = false
                withContext(IO) {
                    repository.removeFavoriteProductFromDatabase(selectedProduct)
                }
            }
        }
    }

    override fun onBtnAddToCartClick() {
        launch {
            message.value = Event(R.string.product_added_to_cart)
        }
    }

    override fun onBtnBackClick() {
        launch {
            navigateToPreviousScreen.value = Event(Unit)
        }
    }

}