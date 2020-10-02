package com.example.code.di.modules

import android.app.Application
import androidx.room.Room
import com.example.code.utils.AppConstants
import com.example.code.utils.api.ApiService
import com.example.code.utils.data.persistence.ProductDatabase
import com.example.code.utils.data.persistence.dao.ProductDao
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule {

        @Provides
        @Singleton
        fun provideOkHttpClient(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(25, TimeUnit.SECONDS)
                .readTimeout(25, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS)
                .build()
        }

        @Provides
        @Singleton
        fun provideRetrofit(okHttpClient: OkHttpClient): ApiService {
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(ApiService::class.java)
        }

        @Provides
        @Singleton
        fun provideProductsDatabase(application: Application): ProductDatabase {
            return Room.databaseBuilder(application, ProductDatabase::class.java, "products.db")
                .build()
        }

        @Provides
        @Singleton
        fun provideProductDao(productDatabase: ProductDatabase): ProductDao {
            return productDatabase.productDao()
        }

}