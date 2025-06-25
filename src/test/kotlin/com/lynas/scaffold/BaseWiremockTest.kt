package com.lynas.scaffold

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.lynas.scaffold.service.ext.RETRY_COUNT
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.retry.RetryRegistry
import io.github.resilience4j.timelimiter.TimeLimiterRegistry
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.junit.jupiter.Testcontainers


@Import(value = [
    TestcontainersConfiguration::class,
    WireMockTestConfig::class,
]) // Required for flyway (move to base wiremock test)
@Testcontainers(parallel = true) // Required for flyway
@SpringBootTest
class BaseWiremockTest {
    @Autowired
    protected lateinit var circuitBreakerRegistry: CircuitBreakerRegistry
    @Autowired
    protected lateinit var retryRegistry: RetryRegistry
    @Autowired
    protected lateinit var timeLimiterRegistry: TimeLimiterRegistry

    @Autowired
    lateinit var wireMockServer: WireMockServer


    companion object {
        private val wireMockServer: WireMockServer =
            WireMockServer(WireMockConfiguration.options().dynamicPort())

        @JvmStatic
        @DynamicPropertySource
        fun setProperties(registry: DynamicPropertyRegistry) {
            registry.add("resilience4j.retry.instances.githubService.maxAttempts") { RETRY_COUNT }
            registry.add("resilience4j.retry.instances.githubService.waitDuration") { "2000ms" }
            registry.add("resilience4j.circuitbreaker.instances.githubService.waitDurationInOpenState") { "1h" }
            registry.add("resilience4j.circuitbreaker.instances.githubService.automaticTransitionFromOpenToHalfOpenEnabled") { false }
            registry.add("resilience4j.circuitbreaker.instances.githubService.minimumNumberOfCalls") { 2 }
        }
    }
}

@TestConfiguration
class WireMockTestConfig {
    @Bean(initMethod = "start", destroyMethod = "stop")
    fun wireMockServer(): WireMockServer {
        return WireMockServer(WireMockConfiguration.options().dynamicPort())
    }
}