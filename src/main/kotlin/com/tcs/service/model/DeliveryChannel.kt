package com.tcs.service.model

data class DeliveryChannel (
        val deliveryStream: Int?,
        val storeNumber:Long?,
        val startDate: String?,
        val endDate: String?,
        val delivererNumber: Int
) {

}
