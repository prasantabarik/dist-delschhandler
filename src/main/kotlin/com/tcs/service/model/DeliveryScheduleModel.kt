package com.tcs.service.model

import org.springframework.data.annotation.Id

data class DeliveryScheduleModel(
        @Id
        var id: String="",
        val storeNumber: Long?=0,
        val deliveryStreamNumber: Int?=0,
        val deliveryStreamName: String?="",
        val schemaName: String?="",
        val deliverySchemaType: Int?=0,
        val startDate: String?="",
        val endDate: String?=null,
        val notes: String?="",
        var timeTable: List<Timetable>? = listOf(),
        var createdBy : String?="",
        var creationDateTime: String?="",
        var updatedBy : String?="",
        var updateDateTime : String?=""
) {

}