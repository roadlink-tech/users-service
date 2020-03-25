package com.peya.usersservice.application.exception

import com.peya.usersservice.domain.user.exception.EmptyDataException
import com.peya.usersservice.domain.user.exception.InvalidEmailAddressException
import com.peya.usersservice.domain.user.exception.InvalidPhoneNumberException
import com.peya.usersservice.domain.user.exception.UserNotFoundException
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandlerAdvice {

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFound(exception: UserNotFoundException) =
            ResponseEntity.status(NOT_FOUND)
                    .body(ErrorResponse("USER_NOT_EXIST", listOf("User does not exist")))

    @ExceptionHandler(EmptyDataException::class)
    fun handleEmptyData(exception: EmptyDataException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(BAD_REQUEST)
                .body(ErrorResponse(
                        "MISSING_REQUIRED_FIELD",
                        listOf(exception.message!!)
                ))
    }

    @ExceptionHandler(InvalidEmailAddressException::class)
    fun handleInvalidEmail(exception: InvalidEmailAddressException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(BAD_REQUEST)
                .body(ErrorResponse(
                        "INVALID_EMAIL",
                        listOf(exception.message!!)
                ))
    }

    @ExceptionHandler(InvalidPhoneNumberException::class)
    fun handleInvalidPhone(exception: InvalidPhoneNumberException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(BAD_REQUEST)
                .body(ErrorResponse(
                        "INVALID_PHONE",
                        listOf(exception.message!!)
                ))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException) =
            ResponseEntity.status(BAD_REQUEST)
                    .body(ErrorResponse(
                            "INVALID_FIELD",
                            exception.bindingResult.allErrors.map { "'${(it as FieldError).field}' ${it.defaultMessage}" }
                    ))
}