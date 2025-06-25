package com.lynas.scaffold.service.ext

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.configureFor
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.lynas.scaffold.BaseWiremockTest
import org.junit.jupiter.api.Assertions.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestClient
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeoutException
import kotlin.test.Test

class RestClientTimeoutTest: BaseWiremockTest() {

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
    fun `should timeout when over timeout limit`() {
        configureFor("localhost", wireMockServer.port())
        WireMock.stubFor(
            get(urlEqualTo("/timeout-test"))
                .willReturn(
                    aResponse()
                        .withFixedDelay(6000)
                        .withStatus(200)
                        .withBody("delayed response")
                        .withHeader("Content-Type", "application/json")
                )
        )

        val future = githubApiService.get("/timeout-test", emptyMap(), String::class.java)

        val exception = assertThrows(ExecutionException::class.java) {
            future.get()
        }

        // Unwrap root cause
        assert(exception.cause is TimeoutException) {
            "Expected TimeoutException but got ${exception.cause?.javaClass}"
        }
    }
}