package com.lynas.scaffold.repository

import com.lynas.scaffold.entity.AppUser
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface AppUserRepository : JpaRepository<AppUser, UUID> {
    fun findAllBy(pageable: Pageable): Page<AppUser>
}