package com.example.base.presentation.dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.OtherHouses
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.base.components.dashboard.CategorySection
import com.example.base.components.dashboard.DashboardProductSession
import com.example.core.util.ConnectionState
import com.example.core.util.Resource
import com.example.core.util.mainViewModel
import com.example.core_ui.modifier.loading
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DashboardScreen(
    onLihatSemuaBestSellerClick: () -> Unit,
    onLihatSemuaTopRatedClick: () -> Unit,
    onLihatSemuaKategoriClick: () -> Unit,
    onCategoryClick: (String) -> Unit,
    onItemClick: (String) -> Unit
) {
    val viewModel = hiltViewModel<DashboardViewModel>()
    val topRatedState = viewModel.topRatedState.observeAsState()
    val category = viewModel.kategoriState.observeAsState()
    val bestSellerState = viewModel.bestSellerState.observeAsState()
    val bannerState = viewModel.bannerState.observeAsState()

    val topBannerState = rememberPagerState { bannerState.value?.data?.size ?: 1 }
    val bestSellerListState = rememberLazyListState()
    val topRatedListState = rememberLazyListState()

    LaunchedEffect(key1 = true) {
        while (true) {
            delay(3000)
            if (topBannerState.canScrollForward) {
                topBannerState.animateScrollToPage(topBannerState.currentPage + 1)
            } else {
                topBannerState.animateScrollToPage(1)
            }
        }
    }

    LaunchedEffect(key1 = mainViewModel.internetState.value) {
        if (mainViewModel.internetState.value is ConnectionState.Available) {
            viewModel.initFetch()
        }
    }

    Scaffold(
        topBar = {

        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                HorizontalPager(
                    state = topBannerState
                ) { index ->
                    if (bannerState.value is Resource.Loading) {
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 24.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .fillMaxWidth()
                                .height(180.dp)
                                .loading()
                        )
                    }

                    if (bannerState.value is Resource.Success) {
                        bannerState.value?.data?.let {
                            AsyncImage(
                                modifier = Modifier
                                    .padding(horizontal = 24.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .fillMaxWidth()
                                    .height(180.dp),
                                model = it[index].image,
                                contentDescription = "",
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }

            item {
                category.value?.let {
                    CategorySection(
                        state = it,
                        onLihatSemuaClick = onLihatSemuaKategoriClick,
                        onCategoryClick = onCategoryClick
                    )
                }
            }

            item {
                bestSellerState.value?.let {
                    DashboardProductSession(
                        sessionName = "Best Seller",
                        state = bestSellerListState,
                        onLihatSemuaClick = onLihatSemuaBestSellerClick,
                        listState = it,
                        onItemClick = onItemClick
                    )
                }
            }

            item {
                topRatedState.value?.let {
                    DashboardProductSession(
                        sessionName = "Top Rated",
                        state = topRatedListState,
                        onLihatSemuaClick = onLihatSemuaTopRatedClick,
                        listState = it,
                        onItemClick = onItemClick
                    )
                }
            }

            item {
                Box(modifier = Modifier)
            }
        }
    }
}