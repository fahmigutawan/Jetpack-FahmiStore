package com.example.payment.presentation.payment_method

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.util.SnackbarHandler
import com.example.core.util.toCurrencyFormat
import com.example.core_ui.button.MyButton

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PaymentMethodScreen(
    address: String,
    namaShipment: String,
    feeShipment: Long,
    totalHarga: Float,
    onProcessed: () -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { /*TODO*/ },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.ChevronLeft,
                            contentDescription = ""
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.background
            ) {
                MyButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        SnackbarHandler.showSnackbar("Order berhasil dibuat!")
                        onProcessed()
                    }
                ) {
                    Text("Proses")
                }
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Spacer(modifier = Modifier)
            }

            item {
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Alamat Pengiriman",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(address, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            item {
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Metode Pembayaran",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.outline)
                            )
                            Text("(DUMMY) ATM BRI", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }

            item {
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Ringkasan Bayar",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.weight(1f),
                                text = "Total Harga",
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                modifier = Modifier
                                    .weight(1f)
                                    .basicMarquee(Int.MAX_VALUE),
                                maxLines = 1,
                                text = totalHarga.toString().toCurrencyFormat(),
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = TextAlign.End
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.weight(1f),
                                text = "Ongkos Kirim",
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                modifier = Modifier
                                    .weight(1f)
                                    .basicMarquee(Int.MAX_VALUE),
                                maxLines = 1,
                                text = feeShipment.toString().toCurrencyFormat(),
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = TextAlign.End
                            )
                        }

                        Divider(
                            modifier = Modifier.fillMaxWidth(),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.outline
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.weight(1f),
                                text = "Grand Total",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                modifier = Modifier
                                    .weight(1f)
                                    .basicMarquee(Int.MAX_VALUE),
                                maxLines = 1,
                                text = (totalHarga + feeShipment).toString().toCurrencyFormat(),
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.End
                            )
                        }
                    }
                }
            }
        }
    }
//    val viewModel = hiltViewModel<PaymentMethodViewModel>()
}

@Preview
@Composable
fun PrevPaymentMethod() {
    PaymentMethodScreen(
        address = "Perum Ganung Asri, Ganungkidul, Nganjuk, Nganjuk, Jawa Timur",
        namaShipment = "Express",
        feeShipment = 20000,
        totalHarga = 15000.0f,
        onBackClick = {},
        onProcessed = {}
    )
}