package com.example.code.screens.catalog

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.code.base.utils.Event
import com.example.code.rules.TestCoroutinesRule
import com.example.code.utils.data.persistence.entities.ProductEntity
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before


import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CatalogViewModelTest {

     @get:Rule
     var instantExecutorRule = InstantTaskExecutorRule()

     @get:Rule
     var testCoroutinesRule = TestCoroutinesRule()

     @Mock
     lateinit var repository: CatalogRepository

     lateinit var viewModel: CatalogViewModel

     @Before
     fun setUp() {
          viewModel = CatalogViewModel(repository)
     }

     @Test
     fun `should call updateSelectedProductInLocal and send navigateToProductScreen`() = testCoroutinesRule.runBlockingTest {
          // Given
          val product = Mockito.spy(ProductEntity::class.java)
          val mockObserver: Observer<Any> = mock()
          viewModel.navigateToProductScreen.observeForever(mockObserver)

          // When
          viewModel.onProductClick(product)

          // Then
          verify(repository).updateSelectedProductInLocal(product)
          verify(mockObserver).onChanged(any())
     }

     @Test
     fun `should call addFavoriteProductToDatabase and send message when product was not favorite`() = testCoroutinesRule.runBlockingTest {
          // Given
          val product= Mockito.spy(ProductEntity::class.java).apply {
               isFavorite = false
          }
          val mockObserver: Observer<Any> = mock()
          viewModel.message.observeForever(mockObserver)

          // When
          viewModel.onProductFavoriteClick(product)

          // Then
          verify(repository).addFavoriteProductToDatabase(product)
          verify(mockObserver).onChanged(any())
     }

     @Test
     fun `should call removeFavoriteProductFromDatabase when product was favorite`() = testCoroutinesRule.runBlockingTest {
          // Given
          val product = Mockito.spy(ProductEntity::class.java).apply {
               isFavorite = true
          }

          // When
          viewModel.onProductFavoriteClick(product)

          // Then
          verify(repository).removeFavoriteProductFromDatabase(product)
     }



     @Test
     fun `should send products and products amount when product loaded`() = testCoroutinesRule.runBlockingTest {
          // Given
          val products =  ArrayList<ProductEntity>()
          val productsObserver: Observer<Any> = mock()
          val productsAmountObserver: Observer<Any> = mock()

          viewModel.products.observeForever(productsObserver)
          viewModel.productsAmount.observeForever(productsAmountObserver)

          // When
          viewModel.onProductsLoaded(products)

          // Then
          verify(productsObserver).onChanged(any())
          verify(productsAmountObserver).onChanged(any())

     }

}