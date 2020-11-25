package com.tcs.service.controller

import com.microsoft.applicationinsights.TelemetryClient
import com.tcs.service.constant.ExceptionMessage.BAD_REQUEST
import com.tcs.service.constant.ExceptionMessage.NO_DATA_FOUND
import com.tcs.service.constant.ServiceLabels.API_TAG_DESC
import com.tcs.service.constant.ServiceLabels.API_TAG_NAME
import com.tcs.service.constant.ServiceLabels.DATA_FOUND
import com.tcs.service.constant.ServiceLabels.MEDIA_TYPE
import com.tcs.service.constant.ServiceLabels.OPENAPI_DELETE_BY_ID_DEF
import com.tcs.service.constant.ServiceLabels.OPENAPI_GET_BY_ID_DEF
import com.tcs.service.constant.ServiceLabels.OPENAPI_GET_DEF
import com.tcs.service.constant.ServiceLabels.OPENAPI_POST_DEF
import com.tcs.service.constant.ServiceLabels.OPENAPI_PUT_DEF
import com.tcs.service.constant.URLPath.BASE_URI
import com.tcs.service.constant.URLPath.GET_ALL_URI
import com.tcs.service.constant.URLPath.GET_BY_ID_URI
import com.tcs.service.constant.URLPath.POST_PUT_DELETE_URI
import com.tcs.service.model.BaseModel
import com.tcs.service.model.DeliveryScheduleModel
import com.tcs.service.model.Model
import com.tcs.service.model.ServiceResponse
import com.tcs.service.proxy.DeliveryClientService
import com.tcs.service.service.Service
import com.tcs.service.utility.RestTemplateClient
import com.tcs.service.validator.BaseValidator
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.apache.logging.log4j.kotlin.logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(BASE_URI)
@Tag(name = API_TAG_NAME, description = API_TAG_DESC)
class Controller(private val service: Service,
                    private val validator: BaseValidator, private val customService: DeliveryClientService,
                    private  val postService: RestTemplateClient) {

    val logger = logger()

    /**
     * TelemetryClient is responsible for sending events to App Insights
     */
//    @Autowired
//    lateinit var telemetryClient: TelemetryClient

    /**
     * This is a sample of the GET Endpoint
     */
    @Operation(summary = OPENAPI_GET_DEF, description = OPENAPI_GET_DEF, tags = [API_TAG_NAME])
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = DATA_FOUND, content = [
            (Content(mediaType = MEDIA_TYPE, array = (
                    ArraySchema(schema = Schema(implementation = BaseModel::class)))))]),
        ApiResponse(responseCode = "400", description = BAD_REQUEST, content = [Content()]),
        ApiResponse(responseCode = "404", description = NO_DATA_FOUND, content = [Content()])]
    )
    @RequestMapping(value = [GET_ALL_URI], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun get(@RequestParam(required = false) storeNumber:Long?,
            @RequestParam(required = false) deliveryStreamNumber:Int?,
            @RequestParam(required = false) deliveryStreamName:String?,
            @RequestParam(required = false) schemaName:String?,
            @RequestParam(required = false) startDate:String?,
            @RequestParam(required = false) endDate:String?,
            @RequestParam(required = false) notes:String?): ResponseEntity<ServiceResponse> {
        logger.info("Get All")
        var records = mutableListOf<Any>()
//        customService.getdeliveryscheduleall()?.toMutableList()
        return ResponseEntity.ok(ServiceResponse("200",
                "SUCCESS", customService.getdeliveryscheduleall(storeNumber, deliveryStreamNumber,deliveryStreamName,
                schemaName,startDate,endDate,notes)))
    }

    /**
     * This is a sample of the GET Endpoint
     */
    @Operation(summary = OPENAPI_GET_BY_ID_DEF, description = OPENAPI_GET_BY_ID_DEF, tags = [API_TAG_NAME])
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = DATA_FOUND, content = [Content(schema = Schema(implementation = ServiceResponse::class))]),
        ApiResponse(responseCode = "400", description = BAD_REQUEST, content = [Content()]),
        ApiResponse(responseCode = "404", description = NO_DATA_FOUND, content = [Content()])]
    )
    @RequestMapping(value = [GET_BY_ID_URI], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getById(
            @PathVariable id: String
    ): ResponseEntity<ServiceResponse> {
        logger.info("Get by id: ")
        return ResponseEntity.ok(ServiceResponse("200",
                "SUCCESS", service.getById(id).data))
    }

    /**
     * This is a sample of the POST Endpoint
     */
    @Operation(summary = OPENAPI_POST_DEF, description = OPENAPI_POST_DEF, tags = [API_TAG_NAME])
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = DATA_FOUND, content = [Content(schema = Schema(implementation = BaseModel::class))]),
        ApiResponse(responseCode = "400", description = BAD_REQUEST, content = [Content()]),
        ApiResponse(responseCode = "404", description = NO_DATA_FOUND, content = [Content()])]
    )
    @RequestMapping(value = [POST_PUT_DELETE_URI], method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun post(@RequestBody model: DeliveryScheduleModel): ResponseEntity<ServiceResponse> {
        println("model is" + model)

       val  response= postService.postForm(model)
        if (response == null) {
            return ResponseEntity.ok(ServiceResponse("400",
                    "Failure", "Something went wrong in inserting Delivery Schedule"))
        }
        else {
            return ResponseEntity.ok(ServiceResponse("200",
                    "SUCCESS", "Data Successfully Inserted"))
        }
    }



    @Operation(summary = OPENAPI_POST_DEF, description = OPENAPI_POST_DEF, tags = [API_TAG_NAME])
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = DATA_FOUND, content = [Content(schema = Schema(implementation = BaseModel::class))]),
        ApiResponse(responseCode = "400", description = BAD_REQUEST, content = [Content()]),
        ApiResponse(responseCode = "404", description = NO_DATA_FOUND, content = [Content()])]
    )
    @RequestMapping(value = [POST_PUT_DELETE_URI], method = [RequestMethod.PUT], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun put(@RequestBody model: DeliveryScheduleModel): ResponseEntity<ServiceResponse> {
     //   println("model is" + model)

        val  response= postService.updateForm(model)
        if (response == null) {
            return ResponseEntity.ok(ServiceResponse("400",
                    "Failure", "Something went wrong in updating Delivery Schedule"))
        }
        else {
            return ResponseEntity.ok(ServiceResponse("200",
                    "SUCCESS", "Data Successfully Updated"))
        }
    }




    @Operation(summary = OPENAPI_DELETE_BY_ID_DEF, description = OPENAPI_DELETE_BY_ID_DEF, tags = [API_TAG_NAME])
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = DATA_FOUND, content = [Content(schema = Schema(implementation = BaseModel::class))]),
        ApiResponse(responseCode = "400", description = BAD_REQUEST, content = [Content()]),
        ApiResponse(responseCode = "404", description = NO_DATA_FOUND, content = [Content()])]
    )
    @RequestMapping(value = [GET_BY_ID_URI], method = [RequestMethod.DELETE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun delete(@PathVariable id: String): ResponseEntity<ServiceResponse> {

        postService.deleteById(id)
        return ResponseEntity.ok(ServiceResponse("200",
                "SUCCESS", "Data Successfully Deleted"))
    }
}