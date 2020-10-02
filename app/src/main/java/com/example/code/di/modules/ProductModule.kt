package com.example.code.di.modules

import androidx.lifecycle.ViewModel
import com.example.code.di.keys.ViewModelKey
import com.example.code.di.scope.ProductScope
import com.example.code.screens.catalog.CatalogRepository
import com.example.code.screens.catalog.CatalogViewModel
import com.example.code.screens.product.ProductRepository
import com.example.code.screens.product.ProductViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class ProductModule {

        @ProductScope
        @Provides
        @IntoMap
        @ViewModelKey(ProductViewModel::class)
        fun provideProductViewModel(repository: ProductRepository): ViewModel {
            return ProductViewModel(repository)
        }

}