package com.tcs.service.test.cucumbertest.model


data class ServiceResponse(
        var responseCode: String,
        var responseDescription: String,
        var response: Any
)