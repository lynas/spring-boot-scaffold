package com.lynas.testing.process.repository

import com.lynas.testing.process.entity.CodeRepository
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CodeRepositoryRepository : JpaRepository<CodeRepository, UUID> {
}