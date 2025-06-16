package com.lynas.scaffold.service.ext

import com.lynas.scaffold.exception.ClientHttpResponseExceptionHandler
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient

@Service
class GithubApiService(
    val restClient: RestClient,
): ExtRestApiService {
    private val logger = KotlinLogging.logger {}
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
}

fun formatQueryParams(queryParams: Map<String, String>): String {
    if (queryParams.isEmpty()) return ""
    val joinedParams = queryParams.entries.joinToString("&") { "${it.key}=${it.value}" }
    return "?q=$joinedParams"
}

//https://api.github.com/search/repositories?q=language:java created:>2024-05-05&per_page=2