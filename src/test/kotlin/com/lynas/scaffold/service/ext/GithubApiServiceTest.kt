package com.lynas.scaffold.service.ext

import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.lynas.scaffold.TestcontainersConfiguration
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.client.RestClient
import org.testcontainers.junit.jupiter.Testcontainers
import org.wiremock.spring.ConfigureWireMock
import org.wiremock.spring.EnableWireMock

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

    @Test
    fun `should retry and then fallback on 500 errors`() {

        WireMock.stubFor(
            get(urlEqualTo("/retry-test"))
                .willReturn(
                    aResponse()
                        .withStatus(500)
                        .withBody("""{"status":"500"}""")
                )
        )
        assertThrows<HttpServerErrorException.InternalServerError> {
            githubApiService.get("/retry-test", emptyMap(), String::class.java)
        }
        WireMock.verify(5, getRequestedFor(urlEqualTo("/retry-test")))
    }
}