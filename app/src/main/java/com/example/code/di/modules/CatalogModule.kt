package com.example.code.di.modules

import androidx.lifecycle.ViewModel
import com.example.code.di.keys.ViewModelKey
import com.example.code.di.scope.CatalogScope
import com.example.code.di.scope.ProductScope
import com.example.code.screens.catalog.CatalogContract
import com.example.code.screens.catalog.CatalogRepository
import com.example.code.screens.catalog.CatalogViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class CatalogModule {

        @CatalogScope
        @Provides
        @IntoMap
        @ViewModelKey(CatalogViewModel::class)
        fun provideCatalogViewModel(repository: CatalogRepository): ViewModel {
            return CatalogViewModel(repository)
        }

}