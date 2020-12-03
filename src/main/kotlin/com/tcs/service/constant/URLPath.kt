package com.tcs.service.constant

object URLPath {

    const val GET_ALL_URI = "/model"
    const val GET_BY_ID_URI = "/model/{id}"
    const val POST_PUT_DELETE_URI = "/model"
    const val SAMPLE_DATA_JSON_PATH = "./src/test/resources/contracts/jsons/sample.json"
    const val DUMMY_DATA_COLLECTION = "./src/test/resources/contracts/jsons/entity.json"
    const val BASE_URI = "/api/v1/deliverySchedule-Validation-service"
    const val SAMPLE_RESPONSE_JSON_PATH = "./src/test/resources/contracts/jsons/modelresponse.json"
    const val SAMPLE_CONTRACT_JSON_PATH = "./src/test/resources/contracts/jsons/contract-sample-data.json"
//    const val DEL_SCHED_CRUD = "http://localhost:3500/v1.0/invoke/delschcrud.edppublic-delschcrud-dev/method/api/v1/deliveryschedule-crud-service"
//    const val GET_REF_DATA = "http://localhost:3500/v1.0/invoke/getrefdata.edppublic-getrefdata-dev/method/api/v1/getReferenceData/"
    const val DEL_SCHED_CRUD = "http://localhost:8081/api/v1/deliveryschedule-crud-service"
    const val GET_REF_DATA = "http://localhost:8080/api/v1/getReferenceData/"


}