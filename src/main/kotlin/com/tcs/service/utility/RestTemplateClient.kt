package com.tcs.service.utility

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
class RestTemplateClient(private val delclient: DeliveryClientService) {

    @Autowired
    lateinit var restTemplate: RestTemplate


   // val logger: Logger = LoggerFactory.getLogger(this.javaClass)


    fun validationforinsert(params:DeliveryScheduleModel): Boolean {

        var timetablepassedcheckflagcount : Int =0
     params.id = params.storeNumber.toString() + params.startDate + params.deliveryStreamNumber
      //  println(params.deliveryStreamNumber)
        var basepathforgetallservice = "http://getrefdata-edppublic-getrefdata-dev.59ae6b648ca3437aae3a.westeurope.aksapp.io/api/v1/getReferenceData/"
        var parametermap:MutableMap<String, String> = mutableMapOf<String, String>()
     //   println("params is" + params)
     //   println(basepathforgetallservice+"deliveryStream/"+params.deliveryStreamNumber)
        var checkdelstream:List<DeliveryStream>? = Utility.
        convertOne(basepathforgetallservice+"deliveryStream/"+params.deliveryStreamNumber, DeliveryStream(),parametermap)
//                as List<DeliveryStream
        println(checkdelstream)
        parametermap.put("storeNumber",params.storeNumber.toString())
        parametermap.put("deliveryStream",params.deliveryStreamNumber.toString())
        parametermap.put("startDate",params.startDate.toString())
        parametermap.put("endDate", params.endDate.toString())
        var checkdelscheduleoverlap:List<DeliveryScheduleModel>? = Utility.
        convert(basepathforgetallservice+"deliveryschedulesorted", DeliveryScheduleModel(),parametermap)

        println(checkdelscheduleoverlap)

        // timetable validation
//        var delexample= delclient.getdeliveryscheduleall(params.storeNumber,
//                params.deliveryStreamNumber,params.deliveryStreamName,
//                null, params.startDate,
//                params.endDate,null)?.get(0)
      //  var timetalevalid : List<Timetable>?  = delexample?.timeTableList
        var timetablepassed=params.timeTable
        var timetablepassedvalidation:MutableList<Timetable>?= mutableListOf()
        var matchesflag: Boolean
        println(timetablepassed)
        if (timetablepassed != null) {
            timetablepassed.forEach {


                for (i in 0..timetablepassed.size-1){

                    var count : Int =0
               //     println("it " + it)
                //    println(timetablepassed.get(i))

                    if((it .deliveryDay==timetablepassed.get(i).deliveryDay&&it.deliveryTime==timetablepassed.get(i).deliveryTime)||
                            (it.orderDay==timetablepassed.get(i).orderDay&& it.orderTime==timetablepassed.get(i).orderTime)||
                            (it.fillDay==timetablepassed.get(i).fillDay&&it.fillTime==timetablepassed.get(i).fillTime))
                    {
                    //   println("inside timetabe for loop")
                        count++
                    //    println("after count " + count)

                    }

                 //   println("count in if loop outside " + count)
                    if(count == 1) {


                        timetablepassedcheckflagcount ++

                       // println("adding " + timetablepassedcheckflagcount)
                    }
                    else {
                        matchesflag = true
                    }

                }


            }
        }
        println(timetablepassedcheckflagcount)
       // println("validation is " + checkdelstream != null && checkdelscheduleoverlap == null &&  timetablepassedcheckflagcount == timetablepassed?.size)
        println("timetable size is " + timetablepassed?.size )
        println("checkdelstream is " + checkdelstream != null)
        println("see if it gets")
        println(checkdelscheduleoverlap.isNullOrEmpty())
        return checkdelstream != null && checkdelscheduleoverlap.isNullOrEmpty() &&  timetablepassedcheckflagcount == timetablepassed?.size
    }

    fun  postForm(params: DeliveryScheduleModel) : ResponseEntity<ServiceResponse>? {


        val currentDateTime = LocalDateTime.now()


        println(validationforinsert(params))
        if (params.endDate!! <= currentDateTime.format(DateTimeFormatter.ISO_DATE) || params.endDate <= params.startDate.toString()
                || !validationforinsert(params)) {
            return null
        }

        else {


        val url = "http://delschcrud-edppublic-delschcrud-dev.59ae6b648ca3437aae3a.westeurope.aksapp.io/api/v1/deliveryschedule-crud-service/model"
        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_JSON
        val requestParams = LinkedMultiValueMap<String, String>()
        //params.forEach(requestParams::add)
        println("inside postForm")
        println(requestParams)
        val httpEntity = HttpEntity<DeliveryScheduleModel>(params, httpHeaders)
      // return restTemplate.postForObject(url, httpEntity, ResponseEntity<T>::class.java)
       // var serv: ResponseEntity<ServiceResponse>? = null
        println("before calling post url")
        println(url)
        println(httpEntity)
        val response: ResponseEntity<ServiceResponse> = restTemplate.exchange(url, HttpMethod.POST, httpEntity, ServiceResponse::class.java)
        println("after calling post url")
     //    serv = restTemplate.postForObject(url, httpEntity, ServiceResponse::class.java)
        return response
        }
    }



    ///For Put request
    fun  updateForm(params: DeliveryScheduleModel) : ResponseEntity<ServiceResponse>? {


        val currentDateTime = LocalDateTime.now()


        println(validationforinsert(params))
        if (params.endDate!! <= currentDateTime.format(DateTimeFormatter.ISO_DATE) || params.endDate <= params.startDate.toString()
                || !validationforupdate(params)) {
            return null
        }

        else {


            val url = "http://delschcrud-edppublic-delschcrud-dev.59ae6b648ca3437aae3a.westeurope.aksapp.io/api/v1/deliveryschedule-crud-service/model"
            val httpHeaders = HttpHeaders()
            httpHeaders.contentType = MediaType.APPLICATION_JSON
            val requestParams = LinkedMultiValueMap<String, String>()
            //params.forEach(requestParams::add)
            println("inside postForm")
            println(requestParams)
            val httpEntity = HttpEntity<DeliveryScheduleModel>(params, httpHeaders)
            // return restTemplate.postForObject(url, httpEntity, ResponseEntity<T>::class.java)
            // var serv: ResponseEntity<ServiceResponse>? = null
          //  println("before calling post url")
            println(url)
            println(httpEntity)
            val response: ResponseEntity<ServiceResponse> = restTemplate.exchange(url, HttpMethod.POST, httpEntity, ServiceResponse::class.java)
           // println("after calling post url")
            //    serv = restTemplate.postForObject(url, httpEntity, ServiceResponse::class.java)
            return response
        }
    }


    //Validation for put request

    fun validationforupdate(params:DeliveryScheduleModel): Boolean {

        var timetablepassedcheckflagcount : Int =0
        params.id = params.storeNumber.toString() + params.startDate + params.deliveryStreamNumber
        //  println(params.deliveryStreamNumber)
        var basepathforgetallservice = "http://getrefdata-edppublic-getrefdata-dev.59ae6b648ca3437aae3a.westeurope.aksapp.io/api/v1/getReferenceData/"
        var parametermap:MutableMap<String, String> = mutableMapOf<String, String>()
        //   println("params is" + params)
        //   println(basepathforgetallservice+"deliveryStream/"+params.deliveryStreamNumber)
        var checkdelstream:List<DeliveryStream>? = Utility.
        convertOne(basepathforgetallservice+"deliveryStream/"+params.deliveryStreamNumber, DeliveryStream(),parametermap)
//                as List<DeliveryStream
        println(checkdelstream)
        parametermap.put("storeNumber",params.storeNumber.toString())
        parametermap.put("deliveryStream",params.deliveryStreamNumber.toString())
        parametermap.put("startDate",params.startDate.toString())
        parametermap.put("endDate", params.endDate.toString())
        var checkdelscheduleoverlap:List<DeliveryScheduleModel>? = Utility.
        convert(basepathforgetallservice+"deliveryschedulesorted", DeliveryScheduleModel(),parametermap)

        println(checkdelscheduleoverlap)

        // timetable validation
//        var delexample= delclient.getdeliveryscheduleall(params.storeNumber,
//                params.deliveryStreamNumber,params.deliveryStreamName,
//                null, params.startDate,
//                params.endDate,null)?.get(0)
        //  var timetalevalid : List<Timetable>?  = delexample?.timeTableList
        var timetablepassed=params.timeTable
        var timetablepassedvalidation:MutableList<Timetable>?= mutableListOf()
        var matchesflag: Boolean
        println(timetablepassed)
        if (timetablepassed != null) {
            timetablepassed.forEach {


                for (i in 0..timetablepassed.size-1){

                    var count : Int =0
                    //     println("it " + it)
                    //    println(timetablepassed.get(i))

                    if((it .deliveryDay==timetablepassed.get(i).deliveryDay&&it.deliveryTime==timetablepassed.get(i).deliveryTime)||
                            (it.orderDay==timetablepassed.get(i).orderDay&& it.orderTime==timetablepassed.get(i).orderTime)||
                            (it.fillDay==timetablepassed.get(i).fillDay&&it.fillTime==timetablepassed.get(i).fillTime))
                    {
                        //   println("inside timetabe for loop")
                        count++
                        //    println("after count " + count)

                    }

                    //   println("count in if loop outside " + count)
                    if(count == 1) {


                        timetablepassedcheckflagcount ++

                        // println("adding " + timetablepassedcheckflagcount)
                    }
                    else {
                        matchesflag = true
                    }

                }


            }
        }

        return checkdelstream != null  &&  timetablepassedcheckflagcount == timetablepassed?.size
    }

    fun deleteById(id:String) {
        val url = "http://delschcrud-edppublic-delschcrud-dev.59ae6b648ca3437aae3a.westeurope.aksapp.io/api/v1/deliveryschedule-crud-service/model/{id}"

        var parametermap:MutableMap<String, String> = mutableMapOf<String, String>()

        parametermap.put("id" ,id)
        restTemplate.delete(url, parametermap);
    }

}


