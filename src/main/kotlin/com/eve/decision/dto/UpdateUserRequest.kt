package com.eve.decision.dto

data class UpdateUserRequest(
        val id: Long,
        val username: String,
        val password: String,
        val firstName: String,
        val lastName: String,
        val email: String,
        val roles: Collection<String>

)