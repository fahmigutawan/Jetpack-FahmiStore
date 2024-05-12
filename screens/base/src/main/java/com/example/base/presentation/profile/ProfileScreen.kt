package com.example.base.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.OtherHouses
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.util.SnackbarHandler

@Composable
fun ProfileScreen(
    onLogout:() -> Unit
) {
    val viewModel = hiltViewModel<ProfileViewModel>()

    Scaffold {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
            item {
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                )
            }

            item {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(top = 16.dp),
                    text = "Fahmi Noordin Rumagutawan",
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(text = "fahmigutawan@gmail.com", style = MaterialTheme.typography.labelMedium)
            }

            item {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(top = 32.dp, bottom = 16.dp),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(imageVector = Icons.Default.OtherHouses, contentDescription = "")
                        Text(text = "Toko Saya")
                    }
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "")
                        Text(text = "Keranjang")
                    }
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = "")
                        Text(text = "Pengaturan")
                    }
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.deleteAuthData()
                            SnackbarHandler.showSnackbar("Berhasil keluar")
                            onLogout()
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.error
                        )
                        Text(text = "Keluar", color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}