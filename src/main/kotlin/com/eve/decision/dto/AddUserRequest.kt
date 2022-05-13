package com.eve.decision.dto

data class AddUserRequest(
        val username: String,
        val password: String,
        val firstName: String,
        val lastName: String,
        val email: String,
        val roles: Set<String>

)