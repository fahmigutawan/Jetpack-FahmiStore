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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.base.components.dashboard.CategorySection
import com.example.base.components.dashboard.DashboardProductSession
import com.example.core.util.ConnectionState
import com.example.core.util.mainViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DashboardScreen() {
    val viewModel = hiltViewModel<DashboardViewModel>()
    val topBannerState = rememberPagerState { 5 }
    val bestSellerListState = rememberLazyListState()
    val topRatedListState = rememberLazyListState()
    val topRatedState = viewModel.topRatedState.observeAsState()
    val category = viewModel.kategoriState.observeAsState()
    val bestSellerState = viewModel.bestSellerState.observeAsState()

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
    
    LaunchedEffect(key1 = mainViewModel.internetState.value){
        if(mainViewModel.internetState.value is ConnectionState.Available){
            viewModel.initFetch()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { /*TODO*/ },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "")
                    }

                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.OtherHouses, contentDescription = "")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    value = viewModel.search.value,
                    onValueChange = { viewModel.search.value = it },
                    placeholder = {
                        Text(text = "Cari sesuatu")
                    },
                    trailingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "")
                    }
                )
            }

            item {
                HorizontalPager(
                    state = topBannerState
                ) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.LightGray)
                            .fillMaxWidth()
                            .height(180.dp)
                    )
                }
            }

            item {
                category.value?.let {
                    CategorySection(
                        state = it
                    )
                }
            }

            item {
                bestSellerState.value?.let {
                    DashboardProductSession(
                        sessionName = "Best Seller",
                        state = bestSellerListState,
                        onLihatSemuaClick = { /*TODO*/ },
                        onEndReach = { /*TODO*/ },
                        listState = it
                    )
                }
            }

            item {
                topRatedState.value?.let {
                    DashboardProductSession(
                        sessionName = "Best Seller",
                        state = topRatedListState,
                        onLihatSemuaClick = { /*TODO*/ },
                        onEndReach = { /*TODO*/ },
                        listState = it
                    )
                }
            }
        }
    }
}