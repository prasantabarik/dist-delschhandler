package com.tcs.service.model

/**
 * This class is for forming the final Response
 */
data class ServiceResponse(
        var responseCode: String?,
        var responseDescription: String?,
        var response: Any?
)