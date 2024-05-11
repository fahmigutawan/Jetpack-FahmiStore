package com.example.base.presentation.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core_ui.button.MyButton
import com.example.core_ui.topbar.MyTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onBackClick: () -> Unit
) {
    val viewModel = hiltViewModel<LoginViewModel>()

    Scaffold(
        topBar = {
            MyTopBar(
                onBackClick = onBackClick,
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(it),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(bottom = 20.dp),
                text = "Selamat datang ke Fahmi Store",
                style = MaterialTheme.typography.headlineLarge
            )

            Text(text = "Silahkan masukkan data untuk masuk")

            OutlinedTextField(
                modifier = Modifier
                    .padding(top = 50.dp)
                    .fillMaxWidth(),
                value = viewModel.username.value,
                onValueChange = { viewModel.username.value = it },
                label = {
                    Text(text = "Email")
                }
            )

            OutlinedTextField(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                value = viewModel.password.value,
                onValueChange = { viewModel.password.value = it },
                label = {
                    Text(text = "Password")
                },
                visualTransformation = PasswordVisualTransformation()
            )

            MyButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp),
                onClick = { /*TODO*/ }
            ) {
                Text(text = "Masuk")
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Belum punya akun? ")
                    Text(
                        modifier = Modifier.clickable { /*TODO*/ },
                        color = MaterialTheme.colorScheme.primary,
                        text = "Daftar"
                    )
                }
            }
        }
    }
}