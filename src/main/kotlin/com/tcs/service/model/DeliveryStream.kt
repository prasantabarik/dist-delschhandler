package com.tcs.service.model

data class DeliveryStream(
        val deliveryStreamNumber: Int?=0,
        val deliveryStreamName: String?="",
        val replenishmentUsedFlag: String?="",
        val createdBy: String?="",
        val creationDateTime: String?="",
        val updatedBy:String?="'",
        val updatedDateTime: String?=""
) {
}