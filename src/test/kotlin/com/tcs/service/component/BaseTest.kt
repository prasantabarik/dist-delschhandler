package com.tcs.service.component

import org.springframework.beans.factory.annotation.Value

open class BaseTest {

    @Value("\${service.test-data.id}")
    public val dataId: String = "0"
}