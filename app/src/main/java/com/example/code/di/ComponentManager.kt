package com.example.code.di

import android.app.Application
import com.example.code.di.components.AppComponent
import com.example.code.di.components.CatalogComponent
import com.example.code.di.components.DaggerAppComponent
import com.example.code.di.components.ProductComponent

object ComponentManager {
    private lateinit var appComponent: AppComponent
    private var catalogComponent: CatalogComponent? = null
    private var productComponent: ProductComponent? = null

    fun getAppComponent(): AppComponent = appComponent

    fun initAppComponent(application: Application) {
        appComponent = DaggerAppComponent
            .builder()
            .application(application)
            .build()
    }

    fun getCatalogComponent(): CatalogComponent {
        if(catalogComponent == null) {
            catalogComponent = appComponent.catalogComponent().create()
        }
        return catalogComponent!!
    }

    fun clearCatalogComponent() {
        catalogComponent = null
    }

    fun getProductComponent(): ProductComponent {
        if(productComponent == null) {
            productComponent = appComponent.productComponent().create()
        }
        return productComponent!!
    }

    fun clearProductComponent() {
        productComponent = null
    }
}