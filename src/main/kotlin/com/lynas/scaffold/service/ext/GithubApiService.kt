package com.lynas.scaffold.service.ext

import com.lynas.scaffold.exception.ClientHttpResponseExceptionHandler
import mu.KotlinLogging
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.retry.annotation.Retry
import io.github.resilience4j.timelimiter.annotation.TimeLimiter
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient

@Service
class GithubApiService(
    val restClient: RestClient,
): ExtRestApiService {
    private val logger = KotlinLogging.logger {}

    @CircuitBreaker(name = "githubService", fallbackMethod = "fallback")
    @Retry(name = "githubService")
    @TimeLimiter(name = "githubService")
    override fun <T> get(
        url: String,
        queryParams: Map<String, String>,
        responseType: Class<T>
    ): T {
        val uri = "$url${formatQueryParams(queryParams)}"
        logger.info { uri }
        return restClient.get()
            .uri(uri)
            .retrieve()
            .onStatus(ClientHttpResponseExceptionHandler::handleException)
            .body(responseType)
            ?: throw IllegalStateException("Empty response body from $uri")
    }


    fun fallback(ex: Throwable): String {
        logger.error("Fallback triggered due to: ${ex.message}")
        // todo configure
        return "default-response"
    }
}

fun formatQueryParams(queryParams: Map<String, String>): String {
    if (queryParams.isEmpty()) return ""
    val joinedParams = queryParams.entries.joinToString("&") { "${it.key}=${it.value}" }
    return "?q=$joinedParams"
}

//https://api.github.com/search/repositories?q=language:java created:>2024-05-05&per_page=2