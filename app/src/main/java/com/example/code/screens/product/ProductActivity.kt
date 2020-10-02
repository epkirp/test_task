package com.example.code.screens.product

import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product.*
import kotlinx.coroutines.launch
import com.example.code.R
import com.example.code.base.utils.Event
import com.example.code.base.utils.observe
import com.example.code.base.utils.observeEvent
import com.example.code.di.ComponentManager
import com.example.code.utils.data.persistence.entities.ProductEntity

class ProductActivity : ProductContract.View<ProductViewModel>(
    R.layout.activity_product,
    ProductViewModel::class.java
) {

    override fun injectComponent() {
        ComponentManager.getProductComponent().inject(this)
    }

    override fun clearComponent() {
        ComponentManager.clearProductComponent()
    }

    override fun setUpUI() {
        setUpListener()
    }

    private fun setUpListener() {
        toolbar.setNavigationOnClickListener { viewModel.onBtnBackClick() }
        ivFavorite.setOnClickListener { viewModel.onBtnFavoriteClick() }
        btnAddToCart.setOnClickListener { viewModel.onBtnAddToCartClick() }
    }

    override fun observeViewModel() {
        observeEvent(viewModel.message, this::observeMessage)
        observeEvent(viewModel.navigateToPreviousScreen, this::observeNavigateToPreviousScreen)
        observe(viewModel.addedToFavorite, this::observeAddedToFavorite)
        observe(viewModel.product, this::observeProduct)
    }

    override fun observeMessage(event: Event<Int>) {
            with(Snackbar.make(root, getString(event.peekContent()), Snackbar.LENGTH_SHORT)) {
                anchorView = llProductInfo
                show()
            }
    }

    override fun observeAddedToFavorite(added: Boolean) {
            if (added) {
                ivFavorite.setImageResource(R.drawable.ic_favorite_added)
            } else {
                ivFavorite.setImageResource(R.drawable.ic_favorite_not_added)
            }
    }

    override fun observeProduct(product: ProductEntity) {
            tvTitle.text = product.title
            tvPrice.text = getString(R.string.price, product.price)
            if (product.isFavorite) {
                ivFavorite.setImageResource(R.drawable.ic_favorite_added)
            } else {
                ivFavorite.setImageResource(R.drawable.ic_favorite_not_added)
            }
            if (product.image.isNotEmpty()) {
                Picasso.get()
                    .load(product.image)
                    .fit()
                    .centerCrop()
                    .into(ivImage)
            }

    }

    override fun observeNavigateToPreviousScreen(event: Event<Unit>) {
            finish()
    }


}