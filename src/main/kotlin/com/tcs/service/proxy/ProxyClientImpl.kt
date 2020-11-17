package com.tcs.service.proxy

import org.springframework.stereotype.Service
import org.apache.logging.log4j.kotlin.logger
import java.util.*
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.tcs.service.model.DeliveryScheduleModel
import com.tcs.service.utility.Utility
import org.json.JSONObject
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.isEqualTo

@Service
class DeliveryClientService : Deliveryschedule<DeliveryScheduleModel> {
    val logger = logger()
    private val basePath = "http://delschcrud-edppublic-delschcrud-dev.59ae6b648ca3437aae3a.westeurope.aksapp.io/api/v1/deliveryschedule-crud-service"
    override fun getdeliveryscheduleall(storeNumber: Long?, deliveryStreamNumber: Int?, deliveryStreamName: String?,
                                        schemaName: String?, startDate: String?, endDate: String?, notes: String?)
                                        : List<DeliveryScheduleModel>? {
        var mapParams: MutableMap<String, String> = mutableMapOf<String, String>()

        if(storeNumber == null && deliveryStreamNumber == null && deliveryStreamName == null &&
                schemaName == null && startDate == null && endDate == null && notes == null){
            return Utility.convert("$basePath/model", DeliveryScheduleModel(), mapParams)
        }

        if( storeNumber != null) {
//
            mapParams.put("storeNumber", storeNumber.toString());
        }

        if( deliveryStreamNumber != null) {
           mapParams.put("deliveryStreamNumber", deliveryStreamNumber.toString());
        }


        if( schemaName != null) {
            mapParams.put("schemaName", schemaName);
        }

        if( startDate != null) {
            mapParams.put("startDate", startDate);
        }

        if( endDate != null) {
            mapParams.put("endDate", endDate);
        }

        if( notes != null) {
            mapParams.put("notes", notes);
        }
        return Utility.convert("$basePath/model", DeliveryScheduleModel(), mapParams)
    }
    companion object {

        fun convertList(jsonObject: JSONObject): List<DeliveryScheduleModel> {
            return when {
                jsonObject.has("response") -> {
                    val mapper = ObjectMapper().registerKotlinModule()
                    mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
                    mapper.readValue<List<DeliveryScheduleModel>>(jsonObject["response"].toString(),
                            object : TypeReference<List<DeliveryScheduleModel>>() {})
                }
                else -> {
                    listOf()
                }
            }
        }
    }

}