package com.tcs.service.utility

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.tcs.service.model.DeliveryScheduleModel
import com.tcs.service.model.DeliveryStream
import java.util.Arrays

import khttp.get

object Utility {

    fun convert(url: String, objectType: Any, params: MutableMap<String, String>): List<DeliveryScheduleModel>? {

        val jsonObject = get(url = url, params = params).jsonObject
        println(jsonObject)
        return when(true) {
            true -> {
                println("Hi")
                val mapper = ObjectMapper().registerKotlinModule()
                mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
                println(objectType)
                when (objectType) {
                    is DeliveryScheduleModel -> {
                        print(mapper)

                        mapper.readValue(jsonObject["response"].toString(), Array<DeliveryScheduleModel>::class.java).toMutableList()
                    }
//                    is DeliveryStream -> {
//                        println("Hello again")
////                      mapper.readValue<StoreCluster>(jsonObject["response"].toString(), StoreCluster::class.java)
//                        mapper.readValue(jsonObject["response"].toString(),Array<DeliveryStream>::class.java)
//                    }

//                    is  -> {
//                        mapper.readValue<StoreCluster>(jsonObject["response"].toString(), StoreCluster::class.java)
//                    }
                    else -> {
                        null
                    }
                }
            }
            false -> {
                null
            }
        }
    }

    fun convertOne(url: String, objectType: Any, params: MutableMap<String, String>): List<DeliveryStream>? {

        val jsonObject = get(url = url, params = params).jsonObject
        println(jsonObject)
        return when(true) {
            true -> {
                println("Hi")
                val mapper = ObjectMapper().registerKotlinModule()
                mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
                println(objectType)
                when (objectType) {
//
                    is DeliveryStream -> {
                        println("Hello again")
//                      mapper.readValue<StoreCluster>(jsonObject["response"].toString(), StoreCluster::class.java)
                        mapper.readValue(jsonObject["response"].toString(),Array<DeliveryStream>::class.java).toMutableList()
                    }
                    else -> {
                        null
                    }
                }
            }
            false -> {
                null
            }
        }
    }


}