package com.tcs.service.test.cucumbertest

import com.tcs.service.test.cucumbertest.model.ServiceResponse
import io.cucumber.spring.CucumberContextConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import java.io.IOException

/**
 * Common configuration for all cucumber tests
 */
@CucumberContextConfiguration
@SpringBootTest(classes = [CucumberTestApplication::class])
class CucumberIntegrationTest {

    lateinit var latestResponse: ResponseEntity<ServiceResponse>
    var baseUri = "http://localhost:8097/api/v1/service-template"

    @Autowired
    protected var restTemplate: RestTemplate? = null

    @Throws(IOException::class)
    fun executeGet(url: String?) {
        latestResponse = RestTemplate()
                .getForEntity(url!!, ServiceResponse::class)
    }

}