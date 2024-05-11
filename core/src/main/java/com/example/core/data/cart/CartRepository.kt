package com.example.core.data.cart

import com.example.core.data.cart.remote.CartRemoteSource
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val remoteSource: CartRemoteSource
){
}