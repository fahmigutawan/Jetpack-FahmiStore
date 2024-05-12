package com.example.posworktest

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.OtherHouses
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Screenshot
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.base.presentation.dashboard.DashboardScreen
import com.example.base.presentation.login.LoginScreen
import com.example.core.util.ConnectionState
import com.example.core.util.mainViewModel
import com.example.core_ui.bottombar.BottomBarItemData
import com.example.core_ui.bottombar.MyBottomBar
import com.example.core_ui.button.MyButton
import com.example.core_ui.layout.MyLayout
import com.example.posworktest.ui.theme.POSWorkTestTheme
import com.example.core.util.SnackbarHandler
import com.example.product.presentation.detail_product.DetailProductScreen
import com.example.product.presentation.list_product.ListProductScreen
import com.example.product.util.RecommendationType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var mainNavController: NavHostController

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            mainNavController = rememberNavController()
            mainViewModel = viewModel()

            val connectivityManager = getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager
            val localLifecycle = LocalLifecycleOwner.current

            localLifecycle.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    mainViewModel.runInternetStateMonitor(
                        onCheck = {
                            when {
                                connectivityManager.allNetworks.any { network ->
                                    connectivityManager.getNetworkCapabilities(network)
                                        ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
                                } -> ConnectionState.Available

                                else -> ConnectionState.Unavailable
                            }
                        }
                    )
                }
            }

            mainNavController.addOnDestinationChangedListener { _, dest, _ ->
                dest.route?.let {
                    mainViewModel.currentRoute.value = it

                    when {
                        it.startsWith(MainNavRoutes.Dashboard.name) -> {
                            mainViewModel.showTopBar.value = true
                            mainViewModel.showBottomBar.value = true
                        }

                        it.startsWith(MainNavRoutes.Feed.name) -> {
                            mainViewModel.showTopBar.value = false
                            mainViewModel.showBottomBar.value = true
                        }

                        it.startsWith(MainNavRoutes.Aktivitas.name) -> {
                            mainViewModel.showTopBar.value = false
                            mainViewModel.showBottomBar.value = true
                        }

                        it.startsWith(MainNavRoutes.Profile.name) -> {
                            mainViewModel.showTopBar.value = false
                            mainViewModel.showBottomBar.value = true
                        }

                        it.startsWith(MainNavRoutes.ListProduct.name) -> {
                            mainViewModel.showTopBar.value = true
                            mainViewModel.showBottomBar.value = false
                        }

                        else -> {
                            mainViewModel.showTopBar.value = false
                            mainViewModel.showBottomBar.value = false
                        }
                    }
                }
            }

            LaunchedEffect(key1 = mainViewModel.internetState.value) {
                if (mainViewModel.internetState.value is ConnectionState.Unavailable) {
                    SnackbarHandler.showSnackbar("Pastikan internet anda menyala...")
                }
            }

            POSWorkTestTheme {
                if (mainViewModel.showShouldLoginPopup.value) {
                    Dialog(
                        onDismissRequest = {
                            mainViewModel.showShouldLoginPopup.value = false
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(MaterialTheme.colorScheme.background)
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 24.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Harap Login",
                                        style = MaterialTheme.typography.titleMedium
                                    )

                                    IconButton(
                                        onClick = {
                                            mainViewModel.showShouldLoginPopup.value = false
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = ""
                                        )
                                    }
                                }

                                Divider(
                                    thickness = 1.dp,
                                    color = MaterialTheme.colorScheme.outline
                                )

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 24.dp, vertical = 16.dp),
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    Text(

                                        text = "Silahkan login terlebih dahulu untuk mengakses semua fitur yang berhubungan dengan transaksi",
                                        style = MaterialTheme.typography.bodyMedium
                                    )

                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        MyButton(
                                            modifier = Modifier.fillMaxWidth(),
                                            onClick = {
                                                mainNavController.navigate(MainNavRoutes.Login.name)
                                                mainViewModel.showShouldLoginPopup.value = false
                                            }
                                        ) {
                                            Text(text = "Login")
                                        }

                                        TextButton(
                                            onClick = {
                                                mainViewModel.showShouldLoginPopup.value = false
                                            }
                                        ) {
                                            Text(text = "Nanti")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                MyLayout(
                    showSnackbar = mainViewModel.showSnackbar.value,
                    onShowSnackbarChange = { mainViewModel.showSnackbar.value = it },
                    snackbarMsg = mainViewModel.snackbarMsg.value,
                    onSnackbarMsgChange = { mainViewModel.snackbarMsg.value = it },
                    loading = mainViewModel.showLoading.value,
                    onLoadingChange = { mainViewModel.showLoading.value = it }
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            if (mainViewModel.showBottomBar.value) {
                                MyBottomBar(
                                    currentRoute = mainViewModel.currentRoute.value,
                                    onItemClick = {
                                        if (mainViewModel.currentRoute.value != it) {
                                            mainNavController.navigate(it)
                                        }
                                    },
                                    items = listOf(
                                        BottomBarItemData(
                                            route = MainNavRoutes.Dashboard.name,
                                            icon = Icons.Default.Home,
                                            word = "Beranda"
                                        ),
                                        BottomBarItemData(
                                            route = MainNavRoutes.Feed.name,
                                            icon = Icons.Default.Screenshot,
                                            word = "Feed"
                                        ),
                                        BottomBarItemData(
                                            route = MainNavRoutes.Aktivitas.name,
                                            icon = Icons.Default.ListAlt,
                                            word = "Aktivitas"
                                        ),
                                        BottomBarItemData(
                                            route = MainNavRoutes.Profile.name,
                                            icon = Icons.Default.Person,
                                            word = "Profile"
                                        )
                                    )
                                )
                            }
                        },
                        topBar = {
                            if (mainViewModel.showTopBar.value) {
                                TopAppBar(
                                    title = {
                                        BasicTextField(
                                            modifier = Modifier.fillMaxWidth(),
                                            value = mainViewModel.search.value,
                                            onValueChange = { mainViewModel.search.value = it },
                                            maxLines = 1,
                                            keyboardOptions = KeyboardOptions(
                                                imeAction = ImeAction.Search
                                            ),
                                            keyboardActions = KeyboardActions(
                                                onSearch = {
                                                    mainNavController.navigate(
                                                        "${MainNavRoutes.ListProduct.name}?type=${RecommendationType.NONE.name}"
                                                    )
                                                }
                                            ),
                                            decorationBox = {
                                                Box(
                                                    modifier = Modifier
                                                        .padding(horizontal = 10.dp)
                                                        .border(
                                                            border = BorderStroke(
                                                                1.dp,
                                                                color = Color.LightGray,
                                                            ),
                                                            shape = RoundedCornerShape(12.dp)
                                                        )
                                                ) {
                                                    Row(
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Box(
                                                            modifier = Modifier
                                                                .padding(
                                                                    horizontal = 18.dp,
                                                                    vertical = 12.dp
                                                                )
                                                                .weight(1f)
                                                        ) {
                                                            if (mainViewModel.search.value.isEmpty()) {
                                                                Text(
                                                                    text = "Cari sesuatu di sini...",
                                                                    style = MaterialTheme.typography.labelMedium
                                                                )
                                                            }

                                                            it()
                                                        }

                                                        Icon(
                                                            modifier = Modifier
                                                                .padding(horizontal = 12.dp)
                                                                .clickable(
                                                                    onClick = {
                                                                        mainNavController.navigate(
                                                                            "${MainNavRoutes.ListProduct.name}?type=${RecommendationType.NONE.name}"
                                                                        )
                                                                    }
                                                                ),
                                                            imageVector = Icons.Default.Search,
                                                            contentDescription = ""
                                                        )
                                                    }
                                                }
                                            }
                                        )
                                    },
                                    actions = {
                                        IconButton(
                                            onClick = {
                                                if (!mainViewModel.isLogin()) {
                                                    mainViewModel.showShouldLoginPopup.value = true
                                                } else {
                                                    //TODO Handle navigation to cart screen
                                                }
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.ShoppingCart,
                                                contentDescription = ""
                                            )
                                        }

                                        IconButton(onClick = { /*TODO*/ }) {
                                            Icon(
                                                imageVector = Icons.Default.OtherHouses,
                                                contentDescription = ""
                                            )
                                        }
                                    },
                                    colors = TopAppBarDefaults.topAppBarColors(
                                        containerColor = MaterialTheme.colorScheme.background
                                    )
                                )
                            }
                        }
                    ) {
                        NavHost(
                            modifier = Modifier.padding(it),
                            navController = mainNavController,
                            startDestination = MainNavRoutes.Dashboard.name
                        ) {
                            composable(MainNavRoutes.Login.name) {
                                LoginScreen(
                                    onBackClick = { onBackClick() }
                                )
                            }

                            composable(MainNavRoutes.Register.name) {
                                //TODO
                            }

                            composable(MainNavRoutes.Dashboard.name) {
                                DashboardScreen(
                                    onLihatSemuaBestSellerClick = {
                                        mainNavController.navigate(
                                            "${MainNavRoutes.ListProduct.name}?type=${RecommendationType.BEST_SELLER.name}"
                                        )
                                    },
                                    onLihatSemuaTopRatedClick = {
                                        mainNavController.navigate(
                                            "${MainNavRoutes.ListProduct.name}?type=${RecommendationType.TOP_RATED.name}"
                                        )
                                    },
                                    onLihatSemuaKategoriClick = {

                                    },
                                    onCategoryClick = {
                                        mainNavController.navigate(
                                            "${MainNavRoutes.ListProduct.name}?category=${it}"
                                        )
                                    },
                                    onItemClick = {
                                        mainNavController.navigate(
                                            "${MainNavRoutes.DetailProduct.name}?id=${it}"
                                        )
                                    }
                                )
                            }

                            composable(
                                "${MainNavRoutes.ListProduct.name}?type={recommendation_type}",
                                arguments = listOf(
                                    navArgument("recommendation_type") {
                                        type = NavType.StringType
                                    }
                                )
                            ) {
                                val recommendationType =
                                    it.arguments?.getString("recommendation_type")
                                        ?: RecommendationType.NONE.name

                                ListProductScreen(
                                    onBackClick = { onBackClick() },
                                    recommendationType = recommendationType,
                                    onItemClick = {
                                        mainNavController.navigate(
                                            "${MainNavRoutes.DetailProduct.name}?id=${it}"
                                        )
                                    }
                                )
                            }

                            composable(
                                "${MainNavRoutes.ListProduct.name}?category={category}",
                                arguments = listOf(
                                    navArgument("category") {
                                        type = NavType.StringType
                                    }
                                )
                            ) {
                                val category =
                                    it.arguments?.getString("category") ?: ""

                                ListProductScreen(
                                    onBackClick = { onBackClick() },
                                    category = category,
                                    onItemClick = {
                                        mainNavController.navigate(
                                            "${MainNavRoutes.DetailProduct.name}?id=${it}"
                                        )
                                    }
                                )
                            }

                            composable(
                                "${MainNavRoutes.DetailProduct.name}?id={id}",
                                arguments = listOf(
                                    navArgument("id") {
                                        type = NavType.StringType
                                    }
                                )
                            ) {
                                val id = it.arguments?.getString("id") ?: ""

                                DetailProductScreen(
                                    onBackClick = { onBackClick() },
                                    id = id,
                                    onAddToCartClick = {
                                        if (!mainViewModel.isLogin()) {
                                            mainViewModel.showShouldLoginPopup.value = true
                                        } else {
                                            SnackbarHandler.showSnackbar("Berhasil ditambahkan (Dummy)")
                                        }
                                    },
                                    onBeliLangsungClick = {
                                        if (!mainViewModel.isLogin()) {
                                            mainViewModel.showShouldLoginPopup.value = true
                                        } else {
                                            //TODO Handle navigation to form screen
                                        }
                                    },
                                    onCartClick = {
                                        if (!mainViewModel.isLogin()) {
                                            mainViewModel.showShouldLoginPopup.value = true
                                        } else {
                                            //TODO Handle navigation to cart screen
                                        }
                                    }
                                )
                            }

                            composable(MainNavRoutes.Feed.name) {
                                //TODO Handle this later
                            }

                            composable(MainNavRoutes.Aktivitas.name) {
                                //TODO Handle this later
                            }

                            composable(MainNavRoutes.Profile.name) {
                                //TODO Handle this later
                            }
                        }
                    }
                }
            }
        }
    }

    fun onBackClick() {
        if (mainNavController.previousBackStackEntry != null) {
            mainNavController.popBackStack()
        } else {
            this@MainActivity.finish()
        }
    }
}