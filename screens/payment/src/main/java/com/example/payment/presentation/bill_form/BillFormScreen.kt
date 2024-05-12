package com.example.payment.presentation.bill_form

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.util.Resource
import com.example.core.util.SnackbarHandler
import com.example.core.util.toCurrencyFormat
import com.example.core_ui.button.MyButton
import com.example.core_ui.dropdown.BasicDropdownField
import com.example.core_ui.modifier.loading
import com.example.payment.components.bill_form.BillProductCard
import com.example.payment.components.card.ShipmentCard
import com.example.payment.components.card.loading.ShipmentLoadingCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillFormScreen(
    onBackClick: () -> Unit,
    onLanjutClick: (
        address: String,
        namaShipment: String,
        hargaShipment: Long,
        totalHarga: Double
    ) -> Unit,
    id: String
) {
    val viewModel = hiltViewModel<BillFormViewModel>()
    val province = viewModel.provinceState.observeAsState()
    val city = viewModel.cityState.observeAsState()
    val district = viewModel.districtState.observeAsState()
    val village = viewModel.villageState.observeAsState()
    val shipment = viewModel.shipmentChoices.observeAsState()
    val product = viewModel.product.observeAsState()

    LaunchedEffect(key1 = true) {
        viewModel.getProductById(id)
    }

    LaunchedEffect(key1 = viewModel.selectedProvince.value) {
        viewModel.selectedProvince.value?.let {
            viewModel.getAllCityByProvinceId(it.id)
            viewModel.selectedCity.value = null
            viewModel.selectedDistrict.value = null
            viewModel.selectedVillage.value = null
        }
    }

    LaunchedEffect(key1 = viewModel.selectedCity.value) {
        viewModel.selectedCity.value?.let {
            viewModel.getAllDistrictByCityId(it.id)
            viewModel.selectedDistrict.value = null
            viewModel.selectedVillage.value = null
        }
    }

    LaunchedEffect(key1 = viewModel.selectedDistrict.value) {
        viewModel.selectedDistrict.value?.let {
            viewModel.getAllVillageByDistrictId(it.id)
            viewModel.selectedVillage.value = null
        }
    }

    LaunchedEffect(key1 = viewModel.allLocationFilled.value) {
        if (viewModel.allLocationFilled.value) {
            viewModel.getAllShipmentChoices()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { /*TODO*/ },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.Default.ChevronLeft, contentDescription = "")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.background
            ) {
                MyButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if (!viewModel.allDataFilled.value) {
                            SnackbarHandler.showSnackbar("Pastikan semua data telah terisi!")
                            return@MyButton
                        }

                        onLanjutClick(
                            viewModel.parsedAddress.value,
                            viewModel.selectedShipment.value?.name ?: "",
                            viewModel.selectedShipment.value?.fee ?: 0L,
                            viewModel.quantity.value * (product.value?.data?.price ?: .0)
                        )
                    }
                ) {
                    Text(text = "Lanjut ke Pembayaran")
                }
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "Detail Barang",
                        style = MaterialTheme.typography.titleLarge
                    )

                    if (product.value is Resource.Success) {
                        product.value?.data?.let {
                            BillProductCard(
                                name = it.title,
                                image = it.image,
                                price = (viewModel.quantity.value * it.price).toString()
                                    .toCurrencyFormat(),
                                quantity = viewModel.quantity.value,
                                onQuantityChange = {
                                    viewModel.quantity.value = it
                                }
                            )
                        }
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "Masukkan Alamat Pengiriman",
                        style = MaterialTheme.typography.titleLarge
                    )

                    BasicDropdownField(
                        value = viewModel.selectedProvince.value?.name ?: "",
                        expanded = viewModel.expandProvince.value,
                        onExpandChange = { viewModel.expandProvince.value = it },
                        placeholder = {
                            Text(text = "Pilih Provinsi...")
                        }
                    ) {
                        if (province.value is Resource.Loading) {
                            item {
                                CircularProgressIndicator()
                            }
                        }

                        if (province.value is Resource.Success) {
                            province.value?.data?.let {
                                it.forEach {
                                    item {
                                        DropdownMenuItem(
                                            text = { Text(text = it.name) },
                                            onClick = {
                                                viewModel.selectedProvince.value = it
                                                viewModel.expandProvince.value = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    BasicDropdownField(
                        value = viewModel.selectedCity.value?.name ?: "",
                        expanded = viewModel.expandCity.value,
                        onExpandChange = {
                            viewModel.expandCity.value = it
                        },
                        placeholder = {
                            Text(text = "Pilih Kota...")
                        }
                    ) {
                        if (city.value is Resource.NotLoadedYet) {
                            item {
                                DropdownMenuItem(
                                    text = { Text(text = "Harap pilih provinsi dahulu...") },
                                    onClick = {}
                                )
                            }
                        }
                        if (city.value is Resource.Loading) {
                            item {
                                CircularProgressIndicator()
                            }
                        }
                        if (city.value is Resource.Success) {
                            city.value?.data?.let {
                                it.forEach {
                                    item {
                                        DropdownMenuItem(
                                            text = { Text(text = it.name) },
                                            onClick = {
                                                viewModel.selectedCity.value = it
                                                viewModel.expandCity.value = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    BasicDropdownField(
                        value = viewModel.selectedDistrict.value?.name ?: "",
                        expanded = viewModel.expandDistrict.value,
                        onExpandChange = {
                            viewModel.expandDistrict.value = it
                        },
                        placeholder = {
                            Text(text = "Pilih Kecamatan...")
                        }
                    ) {
                        if (district.value is Resource.NotLoadedYet) {
                            item {
                                DropdownMenuItem(
                                    text = { Text(text = "Harap pilih kota dahulu...") },
                                    onClick = {}
                                )
                            }
                        }
                        if (district.value is Resource.Loading) {
                            item {
                                CircularProgressIndicator()
                            }
                        }

                        if (district.value is Resource.Success) {
                            district.value?.data?.let {
                                it.forEach {
                                    item {
                                        DropdownMenuItem(
                                            text = { Text(text = it.name) },
                                            onClick = {
                                                viewModel.selectedDistrict.value = it
                                                viewModel.expandDistrict.value = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    BasicDropdownField(
                        value = viewModel.selectedVillage.value?.name ?: "",
                        expanded = viewModel.expandVilalge.value,
                        onExpandChange = {
                            viewModel.expandVilalge.value = it
                        },
                        placeholder = {
                            Text(text = "Pilih Kelurahan...")
                        }
                    ) {
                        if (village.value is Resource.NotLoadedYet) {
                            item {
                                DropdownMenuItem(
                                    text = { Text(text = "Harap pilih kecamatan dahulu...") },
                                    onClick = {}
                                )
                            }
                        }
                        if (village.value is Resource.Loading) {
                            item {
                                CircularProgressIndicator()
                            }
                        }

                        if (village.value is Resource.Success) {
                            village.value?.data?.let {
                                it.forEach {
                                    item {
                                        DropdownMenuItem(
                                            text = { Text(text = it.name) },
                                            onClick = {
                                                viewModel.selectedVillage.value = it
                                                viewModel.expandVilalge.value = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 150.dp),
                        value = viewModel.detailAddress.value,
                        onValueChange = {
                            viewModel.detailAddress.value = it
                        },
                        placeholder = {
                            Text(text = "Masukkan detail tambahan tentang alamat anda...")
                        }
                    )
                }
            }

            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        text = "Pilih Metode Pengiriman",
                        style = MaterialTheme.typography.titleLarge
                    )

                    if (shipment.value is Resource.NotLoadedYet || !viewModel.allLocationFilled.value) {
                        Text(
                            modifier = Modifier.padding(horizontal = 24.dp),
                            text = "* Masukkan lokasi lengkap agar dapat memilih metode pengiriman",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }

                    if (shipment.value is Resource.Loading) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 24.dp)
                                    .loading(),
                                text = "TIPE",
                                style = MaterialTheme.typography.titleSmall
                            )

                            Row(
                                modifier = Modifier.horizontalScroll(rememberScrollState()),
                                horizontalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                Spacer(modifier = Modifier.width(10.dp))

                                repeat(5) {
                                    ShipmentLoadingCard()
                                }

                                Spacer(modifier = Modifier.width(10.dp))
                            }
                        }
                    }

                    if (shipment.value is Resource.Success) {
                        shipment.value?.data?.let {
                            it.forEach { item ->
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        modifier = Modifier.padding(horizontal = 24.dp),
                                        text = item.type,
                                        style = MaterialTheme.typography.titleSmall
                                    )

                                    Row(
                                        modifier = Modifier.horizontalScroll(rememberScrollState()),
                                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                                    ) {
                                        Spacer(modifier = Modifier.width(10.dp))

                                        item.datas.forEach {
                                            ShipmentCard(
                                                name = it.name,
                                                price = it.fee.toString().toCurrencyFormat(),
                                                selected = viewModel.selectedShipment.value?.id == it.id,
                                                onClick = {
                                                    viewModel.selectedShipment.value = it
                                                }
                                            )
                                        }

                                        Spacer(modifier = Modifier.width(10.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}