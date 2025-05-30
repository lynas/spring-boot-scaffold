package com.lynas.testing.process.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "CODE_REPOSITORY")
class CodeRepository {
    @Id
    lateinit var id: UUID
    @Column(name = "USER_ID")
    lateinit var userId: UUID
    lateinit var name: String
    lateinit var language: String
    @Column(name = "STAR_COUNT")
    var starCount: Long = 0L
    @Column(name = "FORK_COUNT")
    var forkCount: Long = 0L
    @Column(name = "CREATED_AT")
    lateinit var createdAt: Instant
    @Column(name = "UPDATED_AT")
    lateinit var updatedAt: Instant
}