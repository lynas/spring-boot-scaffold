package com.lynas.scaffold.entity

import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "APP_USER")
class AppUser : BaseEntity() {
    lateinit var name: String
    @OneToMany(mappedBy = "userId")
    lateinit var repositorySet: MutableSet<CodeRepository>
}