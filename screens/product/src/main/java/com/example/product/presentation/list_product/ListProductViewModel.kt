package com.example.product.presentation.list_product

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.kategori.KategoriRepository
import com.example.core.data.product.ProductRepository
import com.example.core.model.response.product.SingleProductResponse
import com.example.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val categoryRepository: KategoriRepository
) : ViewModel() {
    val productState =
        MutableLiveData<Resource<List<SingleProductResponse>>>(Resource.NotLoadedYet())
    val categoryState = MutableLiveData<Resource<List<String>>>(Resource.NotLoadedYet())
    val selectedCategory = mutableStateOf<String?>(null)

    fun getBestSellerProduct() {
        viewModelScope.launch {
            productRepository.getAllBestSellerProducts().collect {
                productState.postValue(it)
            }
        }
    }

    fun getAllCategory() {
        viewModelScope.launch {
            categoryRepository.getAllCategory().collect {
                categoryState.postValue(it)
            }
        }
    }

    fun getTopRatedProduct() {
        viewModelScope.launch {
            productRepository.getAllTopRatedProducts().collect {
                productState.postValue(it)
            }
        }
    }

    fun getAllProduct() {
        viewModelScope.launch {
            productRepository.getAllProduct().collect {
                productState.postValue(it)
            }
        }
    }

    fun getProductByCategory(category: String) {
        viewModelScope.launch {
            productRepository.getProductByCategory(category).collect {
                productState.postValue(it)
            }
        }
    }
}