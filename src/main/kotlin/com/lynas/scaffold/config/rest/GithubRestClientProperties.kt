package com.lynas.scaffold.config.rest

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("app.rest.client.github")
data class GithubRestClientProperties(
    var url: String,
    var username: String,
    var password: String,
)
