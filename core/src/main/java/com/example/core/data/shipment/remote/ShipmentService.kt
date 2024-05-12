package com.example.core.data.shipment.remote

import com.example.core.model.response.shipment.SingleShipmentResponse
import retrofit2.Response
import retrofit2.http.GET

interface ShipmentService {
    @GET("shipment")
    suspend fun getAllShipmentChoices(): Response<List<SingleShipmentResponse>>
}