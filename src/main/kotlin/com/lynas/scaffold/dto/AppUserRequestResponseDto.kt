package com.lynas.scaffold.dto

import jakarta.validation.constraints.NotBlank
import java.util.UUID

data class AppUserResponseDto(
    val id: UUID,
    val name: String,
    val repoNames: Set<String>,
)

data class AllAppUserResponseDto(
    val data: List<AppUserResponseDto>,
    val pageInfo: PageInfo,
)

data class NewAppUserRequestDto(
    @field:NotBlank
    val name: String,
)

data class NewAppUserResponseDto(
    val id: UUID,
    val name: String,
)