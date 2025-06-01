package com.lynas.scaffold.repository

import com.lynas.scaffold.entity.CodeRepository
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CodeRepositoryRepository : JpaRepository<CodeRepository, UUID> {
}