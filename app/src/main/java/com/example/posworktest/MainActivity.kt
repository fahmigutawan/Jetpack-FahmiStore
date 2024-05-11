package com.example.posworktest

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.base.presentation.dashboard.DashboardScreen
import com.example.base.presentation.login.LoginScreen
import com.example.core.util.ConnectionState
import com.example.core.util.MainViewModel
import com.example.core.util.mainViewModel
import com.example.core_ui.layout.MyLayout
import com.example.posworktest.ui.theme.POSWorkTestTheme
import com.example.posworktest.util.SnackbarHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var mainNavController: NavHostController

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            mainNavController = rememberNavController()
            mainViewModel = viewModel()

            val connectivityManager =
                getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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

            LaunchedEffect(key1 = mainViewModel.internetState.value){
                if(mainViewModel.internetState.value is ConnectionState.Unavailable){
                    SnackbarHandler.showSnackbar("Pastikan internet anda menyala...")
                }
            }

            POSWorkTestTheme {
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
                                //TODO Handle this later
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
                                DashboardScreen()
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