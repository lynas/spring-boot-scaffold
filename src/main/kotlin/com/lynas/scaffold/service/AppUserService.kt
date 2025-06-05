package com.lynas.scaffold.service

import com.lynas.scaffold.dto.AllAppUserResponseDto
import com.lynas.scaffold.dto.AppUserResponseDto
import com.lynas.scaffold.dto.NewAppUserRequestDto
import com.lynas.scaffold.dto.NewAppUserResponseDto
import com.lynas.scaffold.dto.PageInfo
import com.lynas.scaffold.entity.AppUser
import com.lynas.scaffold.exception.AppUserNotFoundByIdException
import com.lynas.scaffold.repository.AppUserRepository
import jakarta.transaction.Transactional
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AppUserService(
    val appUserRepository: AppUserRepository,
) {
    fun getAllUsers(pageable: Pageable): AllAppUserResponseDto {
        val result = appUserRepository.findAllBy(pageable)
        val mappedResult: List<AppUserResponseDto> =
            result.map { appUser -> AppUserResponseDto(appUser.id, appUser.name, emptySet()) }.toList()
        return AllAppUserResponseDto(
            data = mappedResult,
            pageInfo = PageInfo(
                pageNumber = result.pageable.pageNumber,
                pageSize = result.count(),
                totalPage = result.totalPages,
            )
        )
    }

    @Transactional
    fun getByUserId(userId: UUID): AppUserResponseDto {
        val appUser = appUserRepository.findByIdWithRepositories(userId)
            ?: throw AppUserNotFoundByIdException(userId)
        val repoNames = appUser.repositorySet.map { repo -> repo.name }.toSet()
        return AppUserResponseDto(
            id = appUser.id,
            name = appUser.name,
            repoNames = repoNames,
        )
    }

    fun createANewUser(requestBody: NewAppUserRequestDto): NewAppUserResponseDto {
        val appUser = AppUser().apply {
            name = requestBody.name
            appUserRepository.save(this)
        }
        return NewAppUserResponseDto(
            name = appUser.name,
            id = appUser.id,
        )
    }
}