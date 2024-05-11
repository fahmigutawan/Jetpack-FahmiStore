package com.example.base.components.dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.base.components.card.CategoryCard
import com.example.base.components.card.loading.CategoryLoadingCard
import com.example.core.util.Resource

@Composable
fun CategorySection(
    modifier: Modifier = Modifier,
    state: Resource<List<String>>,
    onLihatSemuaClick:() -> Unit
) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Kategori")
            TextButton(onClick = onLihatSemuaClick) {
                Text(text = "Lihat Semua")
            }
        }

        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(modifier = Modifier.width(12.dp))

            if (state is Resource.Success) {
                state.data?.let {
                    it.forEach {
                        CategoryCard(
                            name = it,
                            onClick = {
                                /*TODO handle this later*/
                            }
                        )
                    }
                }
            }

            if (state is Resource.Loading) {
                repeat(5) {
                    CategoryLoadingCard()
                }
            }

            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}