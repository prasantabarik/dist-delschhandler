package com.tcs.service.contract;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import com.tcs.service.controller.Controller;
import com.tcs.service.model.Model;
import com.tcs.service.repository.Repository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import java.io.File;
import java.util.Optional;

import static com.tcs.service.constant.URLPath.DUMMY_DATA_COLLECTION;
import static org.mockito.Mockito.doReturn;

/**
 * This class is for setup Contract Testing.
 * The contract is mentioned in the groovy script inside test/resources/contracts
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@DirtiesContext
@AutoConfigureMessageVerifier
public class Provider {

    @Value("${service.test-data.id}")
    public String dataId = "0";

    @Autowired
    private Controller controller;
    @MockBean
    private Repository repository;

    @BeforeEach
    public void setup() {
        StandaloneMockMvcBuilder standaloneMockMvcBuilder
                = MockMvcBuilders.standaloneSetup(controller);
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
    }

    /**
     * Method for preparing stub  data from sample json
     **/

    private Model getModel() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(DUMMY_DATA_COLLECTION);
        mapper.registerModule(new KotlinModule());
        Model value = mapper.readValue(file, Model.class);
        return value;
    }

    @BeforeEach
    public void repositorySetup() throws Exception {

        doReturn(Optional.of(getModel().getData())).when(repository).findById(Integer.parseInt(dataId));

    }
}
