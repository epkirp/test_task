package com.example.code.di.components

import com.example.code.di.modules.CatalogModule
import dagger.Subcomponent
import com.example.code.di.scope.CatalogScope
import com.example.code.screens.catalog.CatalogActivity


@CatalogScope
@Subcomponent(modules = [CatalogModule::class])
interface CatalogComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): CatalogComponent
    }

    fun inject(catalogActivity: CatalogActivity)

}