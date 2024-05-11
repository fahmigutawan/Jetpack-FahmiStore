package com.example.product.presentation.list_product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.base.components.card.CategoryCard
import com.example.base.components.card.ProductCard
import com.example.base.components.card.loading.CategoryLoadingCard
import com.example.base.components.card.loading.ProductLoadingCard
import com.example.core.util.Resource
import com.example.core.util.toCurrencyFormat
import com.example.product.util.RecommendationType

@Composable
fun ListProductScreen(
    onBackClick: () -> Unit,
    category: String? = null,
    recommendationType: String = RecommendationType.NONE.name
) {
    val viewModel = hiltViewModel<ListProductViewModel>()
    val categoryState = viewModel.categoryState.observeAsState()
    val productState = viewModel.productState.observeAsState()

    LaunchedEffect(key1 = viewModel.selectedCategory.value) {
        if (viewModel.selectedCategory.value != null) {
            viewModel.getProductByCategory(viewModel.selectedCategory.value ?: "")
        } else if (recommendationType == RecommendationType.NONE.name) {
            viewModel.getAllProduct()
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.selectedCategory.value = category

        when (recommendationType) {
            RecommendationType.NONE.name -> {
                viewModel.getAllCategory()
            }

            RecommendationType.TOP_RATED.name -> {
                viewModel.getTopRatedProduct()
            }

            RecommendationType.BEST_SELLER.name -> {
                viewModel.getBestSellerProduct()
            }
        }
    }

    Scaffold {
        Column(
            modifier = Modifier.padding(it),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (recommendationType) {
                RecommendationType.NONE.name -> {
                    LazyRow(
                        modifier = Modifier.padding(top = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            Spacer(modifier = Modifier.width(12.dp))
                        }

                        if (categoryState.value is Resource.Success) {
                            item {
                                CategoryCard(
                                    name = "Semua",
                                    selected = viewModel.selectedCategory.value == null
                                ) {
                                    viewModel.selectedCategory.value = null
                                }
                            }

                            categoryState.value?.data?.let {
                                items(it) { item ->
                                    CategoryCard(
                                        name = item,
                                        selected = viewModel.selectedCategory.value == item
                                    ) {
                                        viewModel.selectedCategory.value = item
                                    }
                                }
                            }
                        }

                        if (categoryState.value is Resource.Loading) {
                            items(5) {
                                CategoryLoadingCard()
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.width(12.dp))
                        }
                    }
                }

                else -> {
                    Text(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        text = when (recommendationType) {
                            RecommendationType.TOP_RATED.name -> "Top Rated"
                            RecommendationType.BEST_SELLER.name -> "Best Seller"
                            else -> ""
                        },
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                if (productState.value is Resource.Success) {
                    productState.value?.data?.let {
                        items(it) { item ->
                            ProductCard(
                                image = item.image,
                                nama = item.title,
                                harga = item.price.toString().toCurrencyFormat(),
                                onClick = {
                                    //TODO Handle this later
                                }
                            )
                        }
                    }
                }

                if (productState.value is Resource.Loading) {
                    items(10) {
                        ProductLoadingCard()
                    }
                }
            }
        }
    }
}