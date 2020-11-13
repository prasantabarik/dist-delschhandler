package com.tcs.service.repository

import com.tcs.service.model.BaseModel
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class CustomRepositoryImpl(private val mongoTemplate: MongoTemplate) : CustomRepository{


    override fun getAllByDesc(modDesc: String): List<BaseModel>{
        return mongoTemplate.find(Query(Criteria.where("modDesc").`is`(modDesc)),
        BaseModel::class.java)
    }
}