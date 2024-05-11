package com.example.core.util

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}