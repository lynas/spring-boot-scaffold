package com.lynas.scaffold.exception

import org.springframework.http.HttpStatus
import org.springframework.http.client.ClientHttpResponse
import java.io.IOException

object ClientHttpResponseExceptionHandler {

    @Throws(IOException::class)
    fun handleException(clientHttpResponse: ClientHttpResponse): Boolean {
        val status = clientHttpResponse.statusCode

        if (status.is4xxClientError) {
            val message = when (status) {
                HttpStatus.NOT_FOUND -> "Repository not found with input language and date"
                HttpStatus.BAD_REQUEST -> "Bad request: check input language and date"
                else -> clientHttpResponse.statusText
            }
            throw APICallException(status.value(), message)
        }

//        if (status.is5xxServerError) {
//            throw APICallException(status.value(), "Internal Server Error")
//        }

        return true
    }
}