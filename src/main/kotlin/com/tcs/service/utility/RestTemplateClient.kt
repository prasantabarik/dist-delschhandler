package com.tcs.service.utility

import com.tcs.service.model.DeliveryScheduleModel
import com.tcs.service.model.ServiceResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.*
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Component
class RestTemplateClient {

    @Autowired
    lateinit var restTemplate: RestTemplate

   // val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    /**
     * Send POST requests and submit parameters through Form forms
     */
    fun  postForm(params: DeliveryScheduleModel) : ResponseEntity<ServiceResponse>? {

        //Validation
        val currentDateTime = LocalDateTime.now()
        if (params.endDate!! <= currentDateTime.format(DateTimeFormatter.ISO_DATE) || params.endDate <= params.startDate.toString()) {
            return null
        }

        else {

            //Post logic//
            val url = "http://localhost:8092/api/v1/service-template/model"
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
}