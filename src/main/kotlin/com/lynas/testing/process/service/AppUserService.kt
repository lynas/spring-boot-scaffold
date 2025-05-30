package com.lynas.testing.process.service

import com.lynas.testing.process.dto.AllAppUserResponseDto
import com.lynas.testing.process.dto.AppUserResponseDto
import com.lynas.testing.process.dto.PageInfo
import com.lynas.testing.process.repository.AppUserRepository
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