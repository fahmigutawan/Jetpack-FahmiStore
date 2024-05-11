package com.example.base.components.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.base.components.card.ProductCard
import com.example.base.components.card.loading.ProductLoadingCard
import com.example.core.model.response.product.SingleProductResponse
import com.example.core.util.Resource
import com.example.core.util.toCurrencyFormat
import com.example.core_ui.layout.MyLazyRow

@Composable
fun DashboardProductSession(
    sessionName: String,
    state: LazyListState,
    onLihatSemuaClick: () -> Unit,
    onItemClick:(String) -> Unit,
    listState: Resource<List<SingleProductResponse>>
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = sessionName)
            TextButton(onClick = onLihatSemuaClick) {
                Text(text = "Lihat Semua")
            }
        }

        LazyRow(
            state = state,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item{
                Spacer(modifier = Modifier.width(10.dp))
            }

            if (listState is Resource.Success) {
                listState.data?.let {
                    items(it) { item ->
                        ProductCard(
                            modifier = Modifier
                                .width(155.dp),
                            image = item.image,
                            nama = item.title,
                            harga = item.price.toString().toCurrencyFormat(),
                            onClick = {
                                onItemClick(item.id)
                            }
                        )
                    }
                }
            }

            if(listState is Resource.Loading){
                items(10){
                    ProductLoadingCard(
                        modifier = Modifier.width(155.dp)
                    )
                }
            }

            item{
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}
