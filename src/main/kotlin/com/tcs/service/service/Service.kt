package com.tcs.service.service

import com.tcs.service.constant.ExceptionMessage
import com.tcs.service.error.customexception.DataNotFoundException
import com.tcs.service.model.Model
import com.tcs.service.repository.Repository
import org.apache.logging.log4j.kotlin.logger
import org.springframework.stereotype.Service


@Service
class Service(private val repository: Repository) {
    val logger = logger()
    fun getById(id: String): Model {
        logger.info("Before Cast")
        return Model(repository.findById(id.toInt()).get() ?: throw DataNotFoundException(ExceptionMessage.NO_DATA_FOUND))
    }


    fun get(): MutableList<Model>{
        //The below lines of code is for POC on Mongo Template
        //repository.getAllByDesc("Sample").forEach{i -> println(i.modId)}
        var models = mutableListOf<Model>()
        var result = repository.findAll() ?: throw DataNotFoundException(ExceptionMessage.NO_DATA_FOUND)
        result.forEach { entity -> models.add(Model(data = entity)) }
        return models
    }

    fun save(model: Model)
    {
        repository.save(model.data)
    }

    fun delete(id: String)
    {
        repository.delete(repository.findById(id.toInt()).get())
    }



}