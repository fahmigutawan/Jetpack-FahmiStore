package com.example.base.components.dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.base.components.card.CategoryCard
import com.example.base.components.card.loading.CategoryLoadingCard
import com.example.core.model.response.kategori.SingleCategoryResponse
import com.example.core.util.Resource
import com.example.core_ui.layout.MyLazyRow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategorySection(
    modifier: Modifier = Modifier,
    state: Resource<List<SingleCategoryResponse>>
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
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "Lihat Semua")
            }
        }

        MyLazyRow {
            item {
                Spacer(modifier = Modifier.padding(horizontal = 12.dp))
            }

            if (state is Resource.Success) {
                state.data?.let { list ->
                    items(list) { item ->
                        CategoryCard(image = item.image, name = item.name)
                    }
                }
            }

            if(state is Resource.Loading){
                items(10){
                    CategoryLoadingCard()
                }
            }

            item {
                Spacer(modifier = Modifier.padding(horizontal = 12.dp))
            }
        }
    }
}