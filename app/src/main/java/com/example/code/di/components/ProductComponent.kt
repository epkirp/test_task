package com.example.code.di.components

import com.example.code.di.modules.ProductModule
import dagger.Subcomponent
import com.example.code.di.scope.ProductScope
import com.example.code.screens.product.ProductActivity

@ProductScope
@Subcomponent(modules = [ProductModule::class])
interface ProductComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ProductComponent
    }

    fun inject(productActivity: ProductActivity)

}