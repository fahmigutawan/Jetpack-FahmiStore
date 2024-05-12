package com.example.core.model.response.shipment

data class SingleShipmentResponse(
    val type: String,
    val datas: List<SingleShipmentDataResponse>
)