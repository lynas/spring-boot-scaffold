package com.lynas.scaffold.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException


class APICallException(statusCode: Int, message: String) :
    ResponseStatusException(HttpStatus.resolve(statusCode)!!, message)