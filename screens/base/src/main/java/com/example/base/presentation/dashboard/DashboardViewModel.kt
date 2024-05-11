package com.example.base.presentation.dashboard

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.banner.BannerRepository
import com.example.core.data.kategori.KategoriRepository
import com.example.core.data.product.ProductRepository
import com.example.core.model.response.banner.SingleBannerResponse
import com.example.core.model.response.product.SingleProductResponse
import com.example.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val kategoriRepository: KategoriRepository,
    private val productRepository: ProductRepository,
    private val bannerRepository: BannerRepository
) : ViewModel() {
    val kategoriState =
        MutableLiveData<Resource<List<String>>>(Resource.NotLoadedYet())
    val bestSellerState =
        MutableLiveData<Resource<List<SingleProductResponse>>>(Resource.NotLoadedYet())
    val topRatedState =
        MutableLiveData<Resource<List<SingleProductResponse>>>(Resource.NotLoadedYet())
    val bannerState =
        MutableLiveData<Resource<List<SingleBannerResponse>>>(Resource.NotLoadedYet())

    fun initFetch() {
        viewModelScope.launch {
            kategoriRepository.getAllCategory().collect {
                kategoriState.postValue(it)
            }
        }

        viewModelScope.launch {
            productRepository.getAllBestSellerProducts().collect {
                bestSellerState.postValue(it)
            }
        }

        viewModelScope.launch {
            productRepository.getAllTopRatedProducts().collect {
                topRatedState.postValue(it)
            }
        }

        viewModelScope.launch {
            bannerRepository.getAllBanner().collect {
                bannerState.postValue(it)
            }
        }
    }

    init {
        initFetch()
    }
}