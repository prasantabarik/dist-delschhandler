package com.tcs.service.utility

import com.tcs.service.constant.URLPath.DEL_SCHED_CRUD
import com.tcs.service.constant.URLPath.GET_ALL_URI
import com.tcs.service.constant.URLPath.GET_BY_ID_URI
import com.tcs.service.constant.URLPath.GET_REF_DATA
import com.tcs.service.model.DeliveryScheduleModel
import com.tcs.service.model.DeliveryStream
import com.tcs.service.model.ServiceResponse
import com.tcs.service.model.Timetable
import com.tcs.service.proxy.DeliveryClientService
import com.tcs.service.proxy.Deliveryschedule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.*
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Component
class RestTemplateClient(private val delClient: DeliveryClientService,private var restTemplate: RestTemplate) {

//    @Autowired
//    lateinit var restTemplate: RestTemplate


//   val logger: Logger = LoggerFactory.getLogger(this.javaClass)


    fun validationForInsert(params:DeliveryScheduleModel): Boolean {

        var timeTablePassedCheckFlagCount : Int =0
        params.id = params.storeNumber.toString() + params.startDate + params.deliveryStreamNumber

        val parameterMap:MutableMap<String, String> = mutableMapOf<String, String>()

        val checkDelStream:List<DeliveryStream>? = Utility.
        convertOne(GET_REF_DATA+"deliveryStream/"+params.deliveryStreamNumber, DeliveryStream(),parameterMap)
        println(checkDelStream)
        parameterMap.put("storeNumber",params.storeNumber.toString())
        parameterMap.put("deliveryStream",params.deliveryStreamNumber.toString())
        parameterMap.put("startDate",params.startDate.toString())
        parameterMap.put("endDate", params.endDate.toString())
        val checkDelScheduleOverlap:List<DeliveryScheduleModel>? = Utility.
        convert(GET_REF_DATA+"deliveryschedulesorted", DeliveryScheduleModel(),parameterMap)

        println(checkDelScheduleOverlap)

        val timeTablePassed=params.timeTable
        var timetablepassedvalidation:MutableList<Timetable>?= mutableListOf()
        var matchesflag: Boolean
        println(timeTablePassed)
        if (timeTablePassed != null) {
            timeTablePassed.forEach {


                for (i in 0..timeTablePassed.size-1){

                    var count : Int =0

                    if((it .deliveryDay==timeTablePassed.get(i).deliveryDay&&it.deliveryTime==timeTablePassed.get(i).deliveryTime)||
                            (it.orderDay==timeTablePassed.get(i).orderDay&& it.orderTime==timeTablePassed.get(i).orderTime)||
                            (it.fillDay==timeTablePassed.get(i).fillDay&&it.fillTime==timeTablePassed.get(i).fillTime))
                    {
                        count++
                    }

                    if(count == 1) {

                        timeTablePassedCheckFlagCount ++

                    }
                    else {
                        matchesflag = true
                        println(matchesflag)
                    }

                }


            }
        }
        println(timeTablePassedCheckFlagCount)
        println("timetable size is " + timeTablePassed?.size )
        println("checkDelStream is " + checkDelStream != null)
        println("see if it gets")
        println(checkDelScheduleOverlap.isNullOrEmpty())
        return checkDelStream != null && checkDelScheduleOverlap.isNullOrEmpty() &&  timeTablePassedCheckFlagCount == timeTablePassed?.size
    }


    fun validationForUpdate(params:DeliveryScheduleModel): Boolean {

        var timeTablePassedCheckFlagCount : Int =0
        params.id = params.storeNumber.toString() + params.startDate + params.deliveryStreamNumber
        val parameterMap:MutableMap<String, String> = mutableMapOf<String, String>()
        val checkDelStream:List<DeliveryStream>? = Utility.
        convertOne(GET_REF_DATA+"deliveryStream/"+params.deliveryStreamNumber, DeliveryStream(),parameterMap)
        println(checkDelStream)
        parameterMap.put("storeNumber",params.storeNumber.toString())
        parameterMap.put("deliveryStream",params.deliveryStreamNumber.toString())
        parameterMap.put("startDate",params.startDate.toString())
        parameterMap.put("endDate", params.endDate.toString())
        val checkDelScheduleOverlap:List<DeliveryScheduleModel>? = Utility.
        convert(GET_REF_DATA+"deliveryschedulesorted", DeliveryScheduleModel(),parameterMap)

        println(checkDelScheduleOverlap)

        val timeTablePassed=params.timeTable
//      var timetablepassedvalidation:MutableList<Timetable>?= mutableListOf()
        var matchesFlag: Boolean
        println(timeTablePassed)
        if (timeTablePassed != null) {
            timeTablePassed.forEach {


                for (i in timeTablePassed.indices){

                    var count : Int =0

                    if((it .deliveryDay==timeTablePassed.get(i).deliveryDay&&it.deliveryTime==timeTablePassed.get(i).deliveryTime)||
                            (it.orderDay==timeTablePassed.get(i).orderDay&& it.orderTime==timeTablePassed.get(i).orderTime)||
                            (it.fillDay==timeTablePassed.get(i).fillDay&&it.fillTime==timeTablePassed.get(i).fillTime))
                    {
                        count++

                    }

                    if(count == 1) {

                        timeTablePassedCheckFlagCount ++

                    }
                    else {
                        matchesFlag = true
                        println(matchesFlag)
                    }

                }


            }
        }

        return checkDelStream != null  &&  timeTablePassedCheckFlagCount == timeTablePassed?.size
    }

    fun  postForm(params: DeliveryScheduleModel) : ResponseEntity<ServiceResponse>? {

        val currentDateTime = LocalDateTime.now()

        println(validationForInsert(params))

        if (params.endDate.toString() <= currentDateTime.format(DateTimeFormatter.ISO_DATE) || params.endDate.toString() <= params.startDate.toString()
                || !validationForInsert(params)) {
            return null
        }

        else {


//      val url = "http://localhost:3500/v1.0/invoke/delschcrud.edppublic-delschcrud-dev/method/api/v1/deliveryschedule-crud-service/model"
        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_JSON
        val requestParams = LinkedMultiValueMap<String, String>()
        println("inside postForm")
        println(requestParams)
        val httpEntity = HttpEntity<DeliveryScheduleModel>(params, httpHeaders)
        println("before calling post url")
        println(DEL_SCHED_CRUD + GET_ALL_URI)
        println(httpEntity)
        val response: ResponseEntity<ServiceResponse> = restTemplate.exchange(DEL_SCHED_CRUD + GET_ALL_URI, HttpMethod.POST, httpEntity, ServiceResponse::class.java)
        println("after calling post url")
        return response
        }
    }


    fun  updateForm(params: DeliveryScheduleModel) : ResponseEntity<ServiceResponse>? {

        val currentDateTime = LocalDateTime.now()

        if (params.endDate.toString() <= currentDateTime.format(DateTimeFormatter.ISO_DATE) || params.endDate.toString() <= params.startDate.toString()
                || !validationForUpdate(params)) {
            return null
        }

        else {

//          val url = "http://localhost:3500/v1.0/invoke/delschcrud.edppublic-delschcrud-dev/method/api/v1/deliveryschedule-crud-service/model"
            val httpHeaders = HttpHeaders()
            httpHeaders.contentType = MediaType.APPLICATION_JSON
            val requestParams = LinkedMultiValueMap<String, String>()
            println("inside postForm")
            println(requestParams)
            val httpEntity = HttpEntity<DeliveryScheduleModel>(params, httpHeaders)
            println(DEL_SCHED_CRUD + GET_ALL_URI)
            println(httpEntity)
            val response: ResponseEntity<ServiceResponse> = restTemplate.exchange(DEL_SCHED_CRUD + GET_ALL_URI, HttpMethod.POST, httpEntity, ServiceResponse::class.java)
            return response
        }
    }



    fun deleteById(id:String) {
//      val url = "http://localhost:3500/v1.0/invoke/delschcrud.edppublic-delschcrud-dev/method/api/v1/deliveryschedule-crud-service/model/{id}"

        val parametermap:MutableMap<String, String> = mutableMapOf<String, String>()

        parametermap.put("id" ,id)
        restTemplate.delete(DEL_SCHED_CRUD + GET_BY_ID_URI, parametermap);
    }

}


