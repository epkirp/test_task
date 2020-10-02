package com.example.code.di.modules

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import com.example.code.di.keys.ViewModelKey
import com.example.code.base.utils.ViewModelFactory
import com.example.code.di.scope.CatalogScope
import com.example.code.di.scope.ProductScope
import com.example.code.screens.catalog.CatalogRepository
import com.example.code.screens.catalog.CatalogViewModel
import com.example.code.screens.product.ProductViewModel
import dagger.Provides
import javax.inject.Singleton


@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
