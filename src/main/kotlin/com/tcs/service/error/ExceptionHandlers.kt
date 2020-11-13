package com.tcs.service.error

import com.tcs.service.error.customexception.DataNotFoundException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import com.tcs.service.error.ErrorResponseEntity.Companion.notFound
import org.apache.logging.log4j.kotlin.logger

@ControllerAdvice
class ExceptionHandlers {

    val logger = logger()
    /**
     * Handler Method for Data Not Found
     */
    @ExceptionHandler(DataNotFoundException::class)
    fun resourceNotFoundException(exception: DataNotFoundException) =
            exception.message?.let { logger.error(exception.stackTrace)
                notFound(it) }

    /**
     * Handler Method for Generic Exception
     */
    @ExceptionHandler(Exception::class)
    fun internalErrorException(exception: Exception) =
            exception.message?.let { logger.error(exception.stackTrace)
                ErrorResponseEntity.serverError(it) }

}