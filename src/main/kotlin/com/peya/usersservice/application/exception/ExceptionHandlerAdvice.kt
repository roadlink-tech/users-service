package com.peya.usersservice.application.exception

import com.peya.usersservice.domain.exception.UserNotFound
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandlerAdvice {

    @ExceptionHandler(UserNotFound::class)
    fun handle(exception: UserNotFound): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(NOT_FOUND)
                .body(ErrorResponse("USR_NOT_EXIST", listOf("User does not exist.l")))
    }
}