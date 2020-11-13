package com.tcs.service.model

import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "delivery-stream")
data class DeliveryStreamModel (
        val deliveryStreamNumber: Int?,
        val deliveryStreamName: String?,
        val replenishmentUsedFlag: String?,
        val deliveryStreamTypeNumber: Int?,
        val createdBy: String?,
        val creationDateTime: Date?,
        val updatedBy : String?,
        val updateDateTime: Date?
)