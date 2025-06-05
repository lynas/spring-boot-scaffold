package com.lynas.scaffold.repository

import com.lynas.scaffold.entity.AppUser
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface AppUserRepository : JpaRepository<AppUser, UUID> {
    fun findAllBy(pageable: Pageable): Page<AppUser>

    @EntityGraph(attributePaths = ["repositorySet"])
    @Query("SELECT u FROM AppUser u WHERE u.id = :userId")
    fun findByIdWithRepositories(@Param("userId") userId: UUID): AppUser?
}