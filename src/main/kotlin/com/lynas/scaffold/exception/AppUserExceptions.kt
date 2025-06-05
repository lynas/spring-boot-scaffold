package com.lynas.scaffold.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

class AppUserNotFoundByIdException(userId: UUID) :
    ResponseStatusException(
        HttpStatus.NOT_FOUND,
        "AppUser with id $userId not found"
    )