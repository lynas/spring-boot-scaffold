package com.lynas.testing.process.repository

import com.lynas.testing.process.entity.AppUser
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface AppUserRepository : JpaRepository<AppUser, UUID> {
    fun findAllBy(pageable: Pageable): Page<AppUser>
}