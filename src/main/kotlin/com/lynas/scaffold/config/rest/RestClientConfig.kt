package com.lynas.scaffold.config.rest

import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
class RestClientProvider(
    private val githubRestClientProperties: GithubRestClientProperties
) {

    @Bean
    fun githubRestClient() : RestClient {
        return RestClient.builder()
            .baseUrl(githubRestClientProperties.url)
            /*.defaultHeaders { header ->
                header.setBasicAuth(
                    githubRestClientProperties.username,
                    githubRestClientProperties.password
                )
            }*/
            .requestInterceptor(RestClientLoggingInterceptor(logger))
            .build()
    }
}

private val logger = KotlinLogging.logger {}
