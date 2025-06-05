package com.lynas.scaffold.controller

import com.lynas.scaffold.dto.ErrorResponse
import com.lynas.scaffold.exception.AppUserNotFoundByIdException
import com.lynas.scaffold.getErrorMessageForInvalidInput
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.Instant

@RestControllerAdvice
class ControllerAdvise {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentTypeMismatchException(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            getErrorMessageForInvalidInput(ex),
            "INVALID_INPUT",
            Instant.now().toString()
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleMethodArgumentTypeMismatchException(ex: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            getErrorMessageForInvalidInput(ex),
            "INVALID_INPUT",
            Instant.now().toString()
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(AppUserNotFoundByIdException::class)
    fun handleEnergyReadingNotFoundByDeviceException(ex: AppUserNotFoundByIdException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            mapOf("error" to ex.message),
            "NOT_FOUND",
            Instant.now().toString()
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

}