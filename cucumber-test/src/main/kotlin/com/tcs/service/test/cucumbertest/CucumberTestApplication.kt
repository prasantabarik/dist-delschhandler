package com.tcs.service.test.cucumbertest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CucumberTestApplication

fun main(args: Array<String>) {
    runApplication<CucumberTestApplication>(*args)
}
