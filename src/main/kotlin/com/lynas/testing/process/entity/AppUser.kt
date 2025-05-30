package com.lynas.testing.process.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "APP_USER")
class AppUser {
    @Id
    lateinit var id: UUID
    lateinit var name: String
}