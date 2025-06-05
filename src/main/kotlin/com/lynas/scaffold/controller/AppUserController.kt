package com.lynas.scaffold.controller

import com.lynas.scaffold.dto.AllAppUserResponseDto
import com.lynas.scaffold.dto.AppUserResponseDto
import com.lynas.scaffold.dto.NewAppUserRequestDto
import com.lynas.scaffold.dto.NewAppUserResponseDto
import com.lynas.scaffold.service.AppUserService
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

const val APP_USER_CONTROLLER_BASE_URL = "/users"

@RestController
@RequestMapping(APP_USER_CONTROLLER_BASE_URL)
class AppUserController(val appUserService: AppUserService) {

    @GetMapping("")
    fun getAllUsers(
        @RequestParam pageNumber: Int?,
        @RequestParam pageSize: Int?,
    ): ResponseEntity<AllAppUserResponseDto> {
        return ResponseEntity.ok().body(
            appUserService.getAllUsers(
                PageRequest.of(
                    pageNumber ?: 0,
                    pageSize ?: 20
                )
            )
        )
    }

    @GetMapping("/{userId}")
    fun getById(
        @PathVariable userId: UUID,
    ): ResponseEntity<AppUserResponseDto> {
        return ResponseEntity.ok().body(appUserService.getByUserId(userId))
    }

    @PostMapping("")
    fun addNewAppUser(@Valid @RequestBody requestBody: NewAppUserRequestDto): ResponseEntity<NewAppUserResponseDto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(appUserService.createANewUser(requestBody))
    }
}

