package com.tcs.service.validator

import com.tcs.service.constant.ExceptionMessage.BAD_PARAMETERS
import com.tcs.service.error.customexception.InvalidInputException
import com.tcs.service.model.BaseModel
import org.springframework.stereotype.Component

@Component
class BaseValidator {

    fun validateBaseModel(model: BaseModel) {
        with(model) {
            require(modId.isEmpty()) { throw InvalidInputException(BAD_PARAMETERS) }
            require(modDesc.isEmpty()) { throw InvalidInputException(BAD_PARAMETERS) }
        }
    }


}