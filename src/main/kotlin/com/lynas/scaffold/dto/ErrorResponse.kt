package com.lynas.scaffold.dto

data class ErrorResponse(
    val errors: Map<String, String>,
    val code: String,
    val timestamp: String
)