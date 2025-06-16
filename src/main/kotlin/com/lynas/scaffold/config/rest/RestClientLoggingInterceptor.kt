package com.lynas.scaffold.config.rest

import mu.KLogger
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse

class RestClientLoggingInterceptor(
    private val logger: KLogger
) : ClientHttpRequestInterceptor {

    override fun intercept(
        request: HttpRequest,
        body: ByteArray,
        execution: ClientHttpRequestExecution
    ): ClientHttpResponse {
        logRequest(request)
        val response = execution.execute(request, body)
        logResponse(response)
        return response
    }

    private fun logRequest(request: HttpRequest) {
            logger.debug("============================ request begin ============================")
            logger.info("Host         : {}", request.uri.host)
            logger.debug("Path         : {}", request.uri.path)
            logger.info("Method       : {}", request.method)
            logger.debug("============================ request end ==============================")
    }

    private fun logResponse(response: ClientHttpResponse) {
            logger.debug("============================ response begin ===========================")
            logger.info("Status code   : {}", response.statusCode)
            logger.debug("Status text   : {}", response.statusText)
            logger.debug("============================ response end =============================")
    }
}
