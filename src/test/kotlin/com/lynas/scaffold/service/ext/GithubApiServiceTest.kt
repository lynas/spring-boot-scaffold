package com.lynas.scaffold.service.ext

import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.lynas.scaffold.TestcontainersConfiguration
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.web.client.RestClient
import org.testcontainers.junit.jupiter.Testcontainers
import org.wiremock.spring.ConfigureWireMock
import org.wiremock.spring.EnableWireMock
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeoutException

const val RETRY_COUNT = 2

@Import(TestcontainersConfiguration::class) // Required for flyway (move to base wiremock test)
@Testcontainers(parallel = true) // Required for flyway
@SpringBootTest(classes = [GithubApiServiceTest.AppConfiguration::class])
@EnableWireMock(
    value = [ConfigureWireMock(
        name = "my-mock", port = 8089
    )]
)
class GithubApiServiceTest {

    @SpringBootApplication
    class AppConfiguration{

        @Bean
        fun restClient(): RestClient {
            return RestClient.builder()
                .baseUrl("http://localhost:8089")
                .build()
        }
    }

    @Autowired
    private lateinit var githubApiService: GithubApiService


    companion object {
        @JvmStatic
        @DynamicPropertySource
        fun setProperties(registry: DynamicPropertyRegistry) {
            registry.add("resilience4j.retry.instances.githubService.maxAttempts") { RETRY_COUNT }
            registry.add("resilience4j.retry.instances.githubService.waitDuration") { "2000ms" }
        }
    }

    @Test
    fun `should retry when 5xx error occurred`() {

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

    @Test
    fun `should timeout when over timeout limit`() {

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