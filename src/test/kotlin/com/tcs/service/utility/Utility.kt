package com.tcs.service.utility

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.tcs.service.constant.URLPath
import com.tcs.service.model.Model
import java.io.File

/**
 * Method for preparing stub  data from sample json
 **/

fun getModel(): Model {
    val mapper = jacksonObjectMapper()
    mapper.registerKotlinModule()
    val jsonString: String = File(URLPath.DUMMY_DATA_COLLECTION).readText(Charsets.UTF_8)
    return mapper.readValue(jsonString)
}