package com.example.payment.presentation.payment_finish

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LibraryAddCheck
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_ui.button.MyButton

@Composable
fun PaymentFinishScreen(onBackClick: () -> Unit) {
    BackHandler {
        onBackClick()
    }

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(200.dp),
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.primary
            )

            Text(
                modifier = Modifier.padding(24.dp),
                text = "Order berhasil dibuat. Tunggu hingga barang datang ke depan rumah anda :)",
                textAlign = TextAlign.Center
            )

            MyButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(top = 64.dp)
                ,
                onClick = onBackClick
            ) {
                Text(text = "Kembali ke Beranda")
            }
        }
    }
}

@Preview
@Composable
fun PrevPaymentFinish() {
    PaymentFinishScreen(
        {}
    )
}