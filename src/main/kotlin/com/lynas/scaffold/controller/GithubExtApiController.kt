package com.lynas.scaffold.controller

import com.lynas.scaffold.dto.GithubRepositoryApiResponseDto
import com.lynas.scaffold.service.ext.GithubApiService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

const val GITHUB_EXT_API_CONTROLLER_BASE_URL = "/github"

@RestController
@RequestMapping(GITHUB_EXT_API_CONTROLLER_BASE_URL)
class GithubExtApiController(
    private val githubApiService: GithubApiService
) {
    @GetMapping("")
    fun searchRepo(
        @RequestParam language: String = "java",
    ): ResponseEntity<GithubRepositoryApiResponseDto> {
        return ResponseEntity.ok().body(
            githubApiService.get(
                url = "/search/repositories",
                queryParams = mapOf("language" to language),
                responseType = GithubRepositoryApiResponseDto::class.java
            ).get()
        )
    }
}