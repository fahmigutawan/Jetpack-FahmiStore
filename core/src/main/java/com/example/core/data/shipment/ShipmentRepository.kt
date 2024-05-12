package com.example.core.data.shipment

import com.example.core.data.shipment.remote.ShipmentRemoteSource
import com.example.core.util.getResponse
import javax.inject.Inject

class ShipmentRepository @Inject constructor(
    private val remote: ShipmentRemoteSource
) {
    fun getAllShipmentChoices() = getResponse {
        remote.getAllShipmentChoices()
    }
}