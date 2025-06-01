package com.lynas.scaffold.service

import com.lynas.scaffold.repository.CodeRepositoryRepository
import org.springframework.stereotype.Service

@Service
class CodeRepositoryService(
    val codeRepositoryRepository: CodeRepositoryRepository,
) {
    fun todo(){
        codeRepositoryRepository.findAll()
    }
}