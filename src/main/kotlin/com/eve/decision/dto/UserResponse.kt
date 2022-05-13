package com.eve.decision.dto

import java.time.LocalDateTime

data class UserResponse(
        val id: Long,
        val username: String,
        val firstName: String,
        val lastName: String,
        val email: String,
        val roles: Set<String>,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime

)