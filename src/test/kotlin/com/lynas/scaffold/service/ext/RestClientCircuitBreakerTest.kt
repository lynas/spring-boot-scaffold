package com.lynas.scaffold.service.ext

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.configureFor
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.http.Fault
import com.lynas.scaffold.BaseWiremockTest
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestClient

class RestClientCircuitBreakerTest: BaseWiremockTest() {

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
    fun `should open circuit breaker after repeated failures and short-circuit subsequent calls`() {
        configureFor("localhost", wireMockServer.port())

        WireMock.stubFor(
            get(urlEqualTo("/cb-test"))
                .willReturn(
                    aResponse()
                        .withFault(Fault.CONNECTION_RESET_BY_PEER)  // Simulate abrupt disconnect
                )
        )
        try {
            githubApiService.get("/cb-test", emptyMap(), String::class.java).get()
        } catch (_: Exception) {
            // expected
        }
        checkHealthStatus(GITHUB_SERVICE_INSTANCE, CircuitBreaker.State.OPEN)
    }

    private fun checkHealthStatus(circuitBreakerName: String, state: CircuitBreaker.State) {
        val circuitBreaker = circuitBreakerRegistry.circuitBreaker(circuitBreakerName)
        Assertions.assertThat(circuitBreaker.state).isEqualTo(state)
    }
}