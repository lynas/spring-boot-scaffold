package com.lynas.scaffold.controller

import com.lynas.scaffold.BaseIntegrationTest
import com.lynas.scaffold.dto.AllAppUserResponseDto
import com.lynas.scaffold.entity.AppUser
import com.lynas.scaffold.getRequestUrl
import com.lynas.scaffold.repository.AppUserRepository
import com.lynas.scaffold.toKObject
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.get
import java.util.UUID

class AppUserControllerIntegrationTest : BaseIntegrationTest() {

    @Autowired
    lateinit var appUserRepository: AppUserRepository

    @Test
    fun `should return 200 success response`() {
        mockMvc.get(generateRequestUrl())
            .andExpect { status { isOk() } }
    }

    @Test
    fun `should get data as defined in page object`(){

        val appUser = appUserRepository.save(AppUser().apply {
            id = UUID.randomUUID()
            name = UUID.randomUUID().toString()
        })
        appUserRepository.save(AppUser().apply {
            id = UUID.randomUUID()
            name = UUID.randomUUID().toString()
        })
        val response = mockMvc.get("${generateRequestUrl()}?pageNumber=0&pageSize=1")
            .andExpect { status { isOk() } }
            .andReturn()
            .response
            .toKObject<AllAppUserResponseDto>()
        assertNotNull(response.data)

        assertEquals(1,response.data.size)
        assertEquals(appUser.name,response.data.first().name)

    }

    private fun generateRequestUrl(): String =
        APP_USER_CONTROLLER_BASE_URL + AppUserController::getAllUsers.getRequestUrl()


}



