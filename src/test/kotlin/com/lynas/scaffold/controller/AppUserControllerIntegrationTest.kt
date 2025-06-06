package com.lynas.scaffold.controller

import com.lynas.scaffold.BaseIntegrationTest
import com.lynas.scaffold.dto.AllAppUserResponseDto
import com.lynas.scaffold.dto.NewAppUserRequestDto
import com.lynas.scaffold.dto.NewAppUserResponseDto
import com.lynas.scaffold.entity.AppUser
import com.lynas.scaffold.getRequestUrl
import com.lynas.scaffold.repository.AppUserRepository
import com.lynas.scaffold.toKObject
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.util.UUID

class AppUserControllerIntegrationTest : BaseIntegrationTest() {

    @Autowired
    lateinit var appUserRepository: AppUserRepository

    @AfterEach
    fun onDestroy() {
        appUserRepository.deleteAll()
    }

    @Test
    fun `should return 200 success response`() {
        mockMvc.get(generateRequestUrlGetAllUsers())
            .andExpect { status { isOk() } }
    }

    @Test
    fun `should get data as defined in page object`() {

        val appUser = appUserRepository.save(AppUser().apply {
            id = UUID.randomUUID()
            name = UUID.randomUUID().toString()
        })
        appUserRepository.save(AppUser().apply {
            id = UUID.randomUUID()
            name = UUID.randomUUID().toString()
        })
        val response = mockMvc.get("${generateRequestUrlGetAllUsers()}?pageNumber=0&pageSize=1")
            .andExpect { status { isOk() } }
            .andReturn()
            .response
            .toKObject<AllAppUserResponseDto>()
        assertNotNull(response.data)

        assertEquals(1, response.data.size)
        assertEquals(appUser.name, response.data.first().name)
    }

    @Test
    fun `should be able to create a new AppUser`() {

        val requestBody = NewAppUserRequestDto("Samantha")
        val response = mockMvc.post(generateRequestUrlCreateNewAppUser()) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(requestBody)
        }.andExpect { status { isCreated() } }
            .andReturn()
            .response
            .toKObject<NewAppUserResponseDto>()
        assertNotNull(response)
        assertEquals(requestBody.name, response.name)
        assertNotNull(response.id)
    }

    private fun generateRequestUrlGetAllUsers(): String =
        APP_USER_CONTROLLER_BASE_URL + AppUserController::getAllUsers.getRequestUrl()

    private fun generateRequestUrlCreateNewAppUser(): String =
        APP_USER_CONTROLLER_BASE_URL + AppUserController::createNewAppUser.getRequestUrl()

}



