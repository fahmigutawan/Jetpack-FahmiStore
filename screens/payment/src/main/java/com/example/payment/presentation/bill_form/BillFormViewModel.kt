package com.example.payment.presentation.bill_form

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.location.LocationRepository
import com.example.core.data.product.ProductRepository
import com.example.core.data.shipment.ShipmentRepository
import com.example.core.model.response.location.SingleCityResponse
import com.example.core.model.response.location.SingleDistrictResponse
import com.example.core.model.response.location.SingleProvinceResponse
import com.example.core.model.response.location.SingleVillageResponse
import com.example.core.model.response.product.SingleProductResponse
import com.example.core.model.response.shipment.SingleShipmentDataResponse
import com.example.core.model.response.shipment.SingleShipmentResponse
import com.example.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BillFormViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val shipmentRepository: ShipmentRepository,
    private val productRepository: ProductRepository
) : ViewModel() {
    val detailAddress = mutableStateOf("")

    val product = MutableLiveData<Resource<SingleProductResponse>>(Resource.NotLoadedYet())
    val quantity = mutableStateOf(1)

    val parsedAddress = derivedStateOf {
        "${detailAddress.value}, ${selectedVillage.value?.name ?: "-"}, ${selectedDistrict.value?.name ?: "-"}, ${selectedCity.value?.name ?: "-"}, ${selectedProvince.value?.name ?: "-"}"
    }

    val provinceState =
        MutableLiveData<Resource<List<SingleProvinceResponse>>>(Resource.NotLoadedYet())
    val cityState = MutableLiveData<Resource<List<SingleCityResponse>>>(Resource.NotLoadedYet())
    val districtState =
        MutableLiveData<Resource<List<SingleDistrictResponse>>>(Resource.NotLoadedYet())
    val villageState =
        MutableLiveData<Resource<List<SingleVillageResponse>>>(Resource.NotLoadedYet())

    val selectedProvince = mutableStateOf<SingleProvinceResponse?>(null)
    val selectedCity = mutableStateOf<SingleCityResponse?>(null)
    val selectedDistrict = mutableStateOf<SingleDistrictResponse?>(null)
    val selectedVillage = mutableStateOf<SingleVillageResponse?>(null)

    val selectedShipment = mutableStateOf<SingleShipmentDataResponse?>(null)

    val expandProvince = mutableStateOf(false)
    val expandCity = mutableStateOf(false)
    val expandDistrict = mutableStateOf(false)
    val expandVilalge = mutableStateOf(false)

    val allLocationFilled = derivedStateOf {
        selectedProvince.value != null
                && selectedCity.value != null
                && selectedDistrict.value != null
                && selectedVillage.value != null
    }
    val allDataFilled = derivedStateOf {
        allLocationFilled.value && selectedShipment.value != null
    }

    val shipmentChoices =
        MutableLiveData<Resource<List<SingleShipmentResponse>>>(Resource.NotLoadedYet())

    fun getAllProvince() {
        viewModelScope.launch {
            locationRepository.getAllProvince().collect {
                provinceState.postValue(it)
            }
        }
    }

    fun getAllCityByProvinceId(id: String) {
        viewModelScope.launch {
            locationRepository.getAllCityByProvinceId(id).collect {
                cityState.postValue(it)
            }
        }
    }

    fun getAllDistrictByCityId(id: String) {
        viewModelScope.launch {
            locationRepository.getAllDistrictByCityId(id).collect {
                districtState.postValue(it)
            }
        }
    }

    fun getAllVillageByDistrictId(id: String) {
        viewModelScope.launch {
            locationRepository.getAllVillageByDistrictId(id).collect {
                villageState.postValue(it)
            }
        }
    }

    fun getAllShipmentChoices() {
        viewModelScope.launch {
            shipmentRepository.getAllShipmentChoices().collect {
                shipmentChoices.postValue(it)
            }
        }
    }

    fun getProductById(id: String) {
        viewModelScope.launch {
            productRepository.getProductById(id).collect {
                product.postValue(it)
            }
        }
    }

    init {
        getAllProvince()
    }
}