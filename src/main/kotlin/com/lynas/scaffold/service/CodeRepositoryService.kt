package com.lynas.scaffold.service

import com.lynas.scaffold.repository.CodeRepositoryRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CodeRepositoryService(
    val codeRepositoryRepository: CodeRepositoryRepository,
) {
    fun todo(){
        codeRepositoryRepository.findAll()
    }

    fun getByUserId(userId: UUID): Any? {
        throw RuntimeException()
    }
}