package com.lynas.scaffold.service

import com.lynas.scaffold.dto.AllAppUserResponseDto
import com.lynas.scaffold.dto.AppUserResponseDto
import com.lynas.scaffold.dto.PageInfo
import com.lynas.scaffold.repository.AppUserRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class AppUserService(val appUserRepository: AppUserRepository) {
    fun getAllUsers(pageable: Pageable): AllAppUserResponseDto {
        val result =  appUserRepository.findAllBy(pageable)
        val mappedResult: List<AppUserResponseDto> = result.map { appUser -> AppUserResponseDto(appUser.id, appUser.name) }.toList()
        return AllAppUserResponseDto(
            data = mappedResult,
            pageInfo = PageInfo(
                pageNumber = result.pageable.pageNumber,
                pageSize = result.count(),
            )
        )
    }
}