package com.lynas.scaffold.controller

import com.lynas.scaffold.service.CodeRepositoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

const val CODE_REPOSITORY_CONTROLLER_BASE_URL = "/repos"

@RestController
@RequestMapping(CODE_REPOSITORY_CONTROLLER_BASE_URL)
class CodeRepositoryController(val codeRepositoryService: CodeRepositoryService) {

    @GetMapping("/{userId}")
    fun getRepositoryByUserId(
        @PathVariable userId: UUID,
    ): ResponseEntity<Object> {
        return ResponseEntity.ok().body(
                codeRepositoryService.getByUserId(userId)
        )
    }
}
