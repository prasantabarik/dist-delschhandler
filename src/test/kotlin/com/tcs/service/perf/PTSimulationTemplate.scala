package com.tcs.service.perf

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties

import scala.concurrent.duration._

class PTSimulationTemplate extends Simulation {

  @Value("${service.perf-urls.get-model-url}")
  val getModelurl: String = ""

  val scn = scenario("Template Check").repeat(100, "n") {
    exec(
      http("GetModel-API")
        .get("http://localhost:8097/api/v1/service-template/model/1")
        //.get(getModelurl)
        .check(status.is(200))
    )
  }
  setUp(scn.inject(atOnceUsers(10))).maxDuration(FiniteDuration.apply(5, "minutes"))
}