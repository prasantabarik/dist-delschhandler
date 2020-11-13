package com.tcs.service.test.cucumbertest


import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
        features = ["src/test/resources/features/"],
        glue = ["com.tcs.service.test.cucumbertest"],
        plugin = ["pretty", "html:target/Destination"],
        monochrome = true
)
class CucumberTest {

}