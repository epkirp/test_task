package com.example.code.screens.catalog

import android.content.Intent
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_catalog.*
import kotlinx.coroutines.launch
import com.example.code.R
import com.example.code.base.utils.Event
import com.example.code.base.utils.observe
import com.example.code.base.utils.observeEvent
import com.example.code.di.ComponentManager
import com.example.code.screens.catalog.adapters.ProductsRecyclerViewAdapter
import com.example.code.screens.product.ProductActivity
import com.example.code.utils.DimensionManager
import com.example.code.utils.GridDividerItemDecoration
import com.example.code.utils.data.persistence.entities.ProductEntity

class CatalogActivity : CatalogContract.View<CatalogViewModel>(
    R.layout.activity_catalog,
    CatalogViewModel::class.java
) {

    private lateinit var productsAdapter: ProductsRecyclerViewAdapter

    override fun injectComponent() {
        ComponentManager.getCatalogComponent().inject(this)
    }

    override fun clearComponent() {
        ComponentManager.clearCatalogComponent()
    }

    override fun setUpUI() {
        setUpAdapters()
        setUpRecyclerViews()
        setUpListeners()
    }

    private fun setUpListeners() {
        productsAdapter.setItemClickListener(object :
            ProductsRecyclerViewAdapter.ItemClickListener {
            override fun onItemFavoriteClick(item: ProductEntity) {
                viewModel.onProductFavoriteClick(item)
            }

            override fun onItemClick(item: ProductEntity) {
                viewModel.onProductClick(item)
            }
        })
    }

    private fun setUpAdapters() {
        productsAdapter = ProductsRecyclerViewAdapter()
    }

    private fun setUpRecyclerViews() {
        with(rvProducts) {
            adapter = productsAdapter
            layoutManager = GridLayoutManager(this@CatalogActivity, 2)
            addItemDecoration(GridDividerItemDecoration(DimensionManager.convertDPToPixels(resources,16)))
        }
    }

    override fun observeViewModel() {
        observe(viewModel.products, this::observeProducts)
        observe(viewModel.productsAmount, this::observeProductsAmount)
        observeEvent(viewModel.navigateToProductScreen, this::observeNavigateToProductScreen)
        observeEvent(viewModel.message, this::observeMessage)
    }


    override fun observeProducts(products: ArrayList<ProductEntity>) {
            productsAdapter.updateItems(products)
    }

    override fun observeNavigateToProductScreen(event: Event<Any>) {
            startActivity(Intent(this@CatalogActivity, ProductActivity::class.java))

    }

    override fun observeMessage(event: Event<Int>) {
            Snackbar.make(rvProducts, getString(event.peekContent()), Snackbar.LENGTH_SHORT)
                .show()
    }

    override fun observeProductsAmount(amount: Int) {
            tvProductsAmount.text = getString(R.string.products_found, amount)
    }

}