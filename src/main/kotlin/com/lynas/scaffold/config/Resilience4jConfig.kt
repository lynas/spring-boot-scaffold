package com.lynas.scaffold.config

import com.lynas.scaffold.service.ext.GITHUB_SERVICE_INSTANCE
import io.github.resilience4j.common.retry.configuration.RetryConfigCustomizer
import java.time.Duration


// TODO does not work, investigate why
//@Configuration
class Resilience4jConfig {

//    @Bean
    fun retryCustomizer(): RetryConfigCustomizer {
        return RetryConfigCustomizer.of(GITHUB_SERVICE_INSTANCE) { builder ->
            builder.maxAttempts(15)
            builder.waitDuration(Duration.ofSeconds(2))
        }
    }
}