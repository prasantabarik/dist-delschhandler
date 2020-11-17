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
        val endDate: String?="",
        val notes: String?="",
        var timeTableList:List<Timetable>?= listOf()
) {

}