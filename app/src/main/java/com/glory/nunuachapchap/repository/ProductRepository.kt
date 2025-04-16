package com.glory.nunuachapchap.repository

import android.content.Context
import com.glory.nunuachapchap.data.ProductDatabase
import com.glory.nunuachapchap.model.Product

class ProductRepository(context: Context) {
    private val productDao = ProductDatabase.getDatabase(context).productDao()

    suspend fun insertProduct(product: Product) {
        productDao.insertProduct(product)
    }

    fun getAllProducts() = productDao.getAllProducts()

    suspend fun deleteProduct(product: Product) = productDao.deleteProduct(product)
}
