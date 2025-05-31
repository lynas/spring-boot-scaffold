package com.lynas.testing.process.controller

import com.lynas.testing.process.dto.AllAppUserResponseDto
import com.lynas.testing.process.service.AppUserService
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

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
}
