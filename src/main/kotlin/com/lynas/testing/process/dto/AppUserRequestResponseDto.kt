package com.lynas.testing.process.dto

import java.util.UUID

data class AppUserResponseDto(
    val id: UUID,
    val name: String,
)

data class AllAppUserResponseDto(
    val data: List<AppUserResponseDto>,
    val pageInfo: PageInfo,
)