package com.example.core.data.shipment.remote

import javax.inject.Inject

class ShipmentRemoteSource @Inject constructor(
    private val service: ShipmentService
) {
    suspend fun getAllShipmentChoices() = service.getAllShipmentChoices()
}