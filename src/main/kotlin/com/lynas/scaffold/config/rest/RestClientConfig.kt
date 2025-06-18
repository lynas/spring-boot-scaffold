package com.lynas.scaffold.config.rest

import mu.KotlinLogging
import org.apache.hc.client5.http.config.RequestConfig
import org.apache.hc.client5.http.impl.classic.HttpClients
import org.apache.hc.core5.util.Timeout
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.retry.annotation.EnableRetry
import org.springframework.web.client.RestClient

@EnableRetry
@Configuration
class RestClientProvider(
    private val githubRestClientProperties: GithubRestClientProperties
) {
    val timeoutMillis = 5000L


    @Bean
    fun githubRestClient() : RestClient {
        return RestClient.builder()
            .baseUrl(githubRestClientProperties.url)
            .defaultHeaders { header ->
                header.setBasicAuth(
                    githubRestClientProperties.username,
                    githubRestClientProperties.password
                )
            }
            .requestFactory(httpComponentsClientHttpRequestFactory())
            .requestInterceptor(RestClientLoggingInterceptor(logger))
            .build()
    }

    fun httpComponentsClientHttpRequestFactory(): HttpComponentsClientHttpRequestFactory {
        val requestConfig = RequestConfig.custom()
            .setConnectionRequestTimeout(Timeout.ofMilliseconds(timeoutMillis))
            .setResponseTimeout(Timeout.ofMilliseconds(timeoutMillis))
            .build()

        val httpClient = HttpClients.custom()
            .setDefaultRequestConfig(requestConfig)
            .build()

        val requestFactory = HttpComponentsClientHttpRequestFactory()
        requestFactory.httpClient = httpClient

        return requestFactory
    }
}

private val logger = KotlinLogging.logger {}
