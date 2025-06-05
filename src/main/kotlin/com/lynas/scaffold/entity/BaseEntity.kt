package com.lynas.scaffold.entity

import jakarta.persistence.Column
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import java.util.UUID

/**
 * An abstract base entity class
 * that should be extended by concrete JPA entities.
 *
 */
@MappedSuperclass
abstract class BaseEntity {
    @Id
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    var id: UUID = UUID.randomUUID()

    /*@Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant = Instant.now()

    @Column(name = "updated_at", nullable = true)
    var updatedAt: Instant? = null

    @PreUpdate
    private fun beforeUpdate() {
        updatedAt = Instant.now()
    }*/
}