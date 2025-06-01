package com.lynas.scaffold.service

import com.lynas.scaffold.dto.AllAppUserResponseDto
import com.lynas.scaffold.entity.AppUser
import com.lynas.scaffold.repository.AppUserRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.util.UUID

class AppUserServiceTest {

    private val appUserRepository: AppUserRepository = mockk(relaxed = true)
    private val appUserService: AppUserService = AppUserService(appUserRepository)

    @Test
    fun `getAllUsers should return all users list`() {
        // Given
        val pageable = PageRequest.of(0, 10)
        val appUsers = listOf(
            AppUser().apply {
                this.id = UUID.randomUUID()
                this.name = "Alice"
            },
            AppUser().apply {
                this.id = UUID.randomUUID()
                this.name = "Bob"
            },
        )
        val page: Page<AppUser> = PageImpl(appUsers, pageable, appUsers.size.toLong())

        every { appUserRepository.findAllBy(pageable) } returns page

        // When
        val result: AllAppUserResponseDto = appUserService.getAllUsers(pageable)

        // then
        assertEquals(appUsers.size, result.data.size)
        assertEquals(appUsers[0].name, result.data[0].name)
        assertEquals(page.pageable.pageNumber, result.pageInfo.pageNumber)
        assertEquals(page.count(), result.pageInfo.pageSize)

    }

}