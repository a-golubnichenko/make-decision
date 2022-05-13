package com.eve.decision.dto

data class JwtResponse(
        val token: String,
        val id: Long?,
        val username: String,
        val email: String,
        val roles: MutableSet<String>

)