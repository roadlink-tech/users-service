package com.peya.usersservice.application.exception

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.peya.usersservice.domain.exception.UserNotFound
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
class ExceptionHandlerAdvice {

    @ExceptionHandler(UserNotFound::class)
    fun handleUserNotFound(exception: UserNotFound) =
        ResponseEntity.status(NOT_FOUND)
                .body(ErrorResponse("USER_NOT_EXIST", listOf("User does not exist")))

    @ExceptionHandler(MissingKotlinParameterException::class)
    fun handleDefaultHandlerExceptionResolver(exception: MissingKotlinParameterException) =
            ResponseEntity.status(BAD_REQUEST)
                    .body(ErrorResponse(
                            "MISSING_REQUIRED_FIELD",
                            listOf("field '${exception.path[0].fieldName}' is required")
                    ))

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException) =
            ResponseEntity.status(BAD_REQUEST)
                    .body(ErrorResponse(
                            "INVALID_FIELD",
                            exception.bindingResult.allErrors.map { "'${(it as FieldError).field}' ${it.defaultMessage}" }
                    ))
}