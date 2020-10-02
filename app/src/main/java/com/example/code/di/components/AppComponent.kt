package com.example.code.di.components

import android.app.Application
import com.example.code.di.modules.AppModule
import com.example.code.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,ViewModelModule::class])
interface AppComponent{

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
    fun catalogComponent(): CatalogComponent.Factory
    fun productComponent(): ProductComponent.Factory
}
