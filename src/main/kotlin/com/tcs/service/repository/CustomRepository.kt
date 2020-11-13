package com.tcs.service.repository

import com.tcs.service.model.BaseModel

interface CustomRepository {

    fun getAllByDesc(modDesc: String): List<BaseModel>
}