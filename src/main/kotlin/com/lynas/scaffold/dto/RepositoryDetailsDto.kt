package com.lynas.scaffold.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
@JvmRecord
data class RepositoryDetailsDto(
    val name: String?,
    val language: String?,
    @field:JsonProperty("stargazers_count")
    @param:JsonProperty("stargazers_count")
    val starCount: Int?,
    @field:JsonProperty("forks_count")
    @param:JsonProperty("forks_count")
    val forkCount: Int?,
    @field:JsonProperty("created_at")
    @param:JsonProperty("created_at")
    val createdAt: String?,
    @field:JsonProperty("updated_at")
    @param:JsonProperty("updated_at")
    val updatedAt: String?,
    @field:JsonProperty("html_url")
    @param:JsonProperty("html_url")
    val url: String?
)
