package com.example.product.presentation.detail_product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.product.ProductRepository
import com.example.core.model.response.product.SingleProductResponse
import com.example.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {
    val productState = MutableLiveData<Resource<SingleProductResponse>>(Resource.NotLoadedYet())

    fun getProductById(id: String) {
        viewModelScope.launch {
            productRepository.getProductById(id).collect {
                productState.postValue(it)
            }
        }
    }
}