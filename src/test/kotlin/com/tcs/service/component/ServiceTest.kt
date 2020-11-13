package com.tcs.service.component

import com.nhaarman.mockito_kotlin.whenever
import com.tcs.service.model.Model
import com.tcs.service.repository.Repository
import com.tcs.service.service.Service
import com.tcs.service.utility.getModel
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.io.File
import java.util.*


@SpringBootTest()
@ExtendWith(SpringExtension::class, MockitoExtension::class)
class ServiceTest: BaseTest() {

    @Autowired
    lateinit var service: Service

    @MockBean
    lateinit var repository: Repository



    @BeforeEach
    fun setup() {
        whenever(repository.findById(dataId.toInt())).thenAnswer {
            Optional.of(getModel().data)
        }

    }


    @Test
    fun `should return model object`() {
        assert(service.getById(dataId) is Model)
    }


}