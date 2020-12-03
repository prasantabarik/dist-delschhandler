package com.tcs.service.proxy

interface Deliveryschedule<T> {
    fun getDeliveryScheduleAll(storeNumber: Long?, deliveryStreamNumber: Int?, deliveryStreamName: String?, schemaName: String?, startDate: String?, endDate: String?, notes: String?):Any?
}