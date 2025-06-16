package com.lynas.scaffold.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class GithubRepositoryApiResponseDto(
    val items: MutableList<RepositoryDetailsDto>
)

