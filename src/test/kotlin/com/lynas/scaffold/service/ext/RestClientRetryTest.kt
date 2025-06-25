package com.lynas.scaffold.service.ext

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.configureFor
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.lynas.scaffold.BaseWiremockTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestClient

const val RETRY_COUNT = 2

class RestClientRetryTest: BaseWiremockTest() {

    @Autowired
    private lateinit var githubApiService: GithubApiService

    @TestConfiguration
    class AppConfiguration {
        @Bean
        fun restClient(wireMockServer: WireMockServer): RestClient =
            RestClient.builder()
                .baseUrl("http://localhost:${wireMockServer.port()}")
                .build()
    }

    @Test
    fun `should retry when 5xx error occurred`() {
        configureFor("localhost",wireMockServer.port())
        retryRegistry.retry(GITHUB_SERVICE_INSTANCE)
        WireMock.stubFor(
            get(urlEqualTo("/retry-test"))
                .willReturn(
                    aResponse()
                        .withStatus(500)
                        .withBody("""{"status":"500"}""")
                )
        )
        try {
            githubApiService.get("/retry-test", emptyMap(), String::class.java).get()
        } catch (e: Exception) { }
        WireMock.verify(RETRY_COUNT, getRequestedFor(urlEqualTo("/retry-test")))
    }
}

