package com.peya.usersservice.domain.exception

import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = NOT_FOUND)
class ResourceNotFound(message: String): Exception(message)