package com.tcs.service.model

import org.springframework.data.annotation.Id

data class DeliveryScheduleModel(
        @Id
        val id: String="",
        val deliveryScheduleId: Long?=0,
        val storeNumber: Long?=0,
        val deliveryStreamNumber: Double?=0.0,
        val deliveryStreamName: String?="",
        val schemaName: String?="",
        val deliverySchemaType: Int?=0,
        val startDate: String?="",
        val endDate: String?="",
        val notes: String?="",
        val _rid: String?="",
        var timeTableList:List<Timetable>?= listOf()
) {

}