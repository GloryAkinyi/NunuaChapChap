package com.glory.nunuachapchap.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.glory.nunuachapchap.model.Product

class ProductViewModel : ViewModel() {
    private val _allProducts = MutableLiveData<List<Product>>(emptyList())
    val allProducts: LiveData<List<Product>> = _allProducts

    fun addProduct(name: String, price: Double,phone:String, imageUri: String) {
        val newList = _allProducts.value.orEmpty().toMutableList()
        newList.add(Product(id = newList.size + 1, name = name, price = price,phone = phone, imagePath = imageUri))
        _allProducts.value = newList
    }

    fun updateProduct(updatedProduct: Product) {
        val newList = _allProducts.value?.map { if (it.id == updatedProduct.id) updatedProduct else it }
        _allProducts.value = newList
    }

    fun deleteProduct(product: Product) {
        val newList = _allProducts.value.orEmpty().toMutableList()
        newList.removeIf { it.id == product.id }
        _allProducts.value = newList
    }
}
