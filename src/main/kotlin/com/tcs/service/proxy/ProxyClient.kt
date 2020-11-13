package com.tcs.service.proxy

interface Deliveryschedule<T> {
    fun getdeliveryscheduleall(storeNumber: Long?, deliveryStreamNumber: Int?, deliveryStreamName: String?, schemaName: String?, startDate: String?, endDate: String?, notes: String?): List<T>?
}