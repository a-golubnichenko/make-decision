package com.eve.decision.dto

import java.time.LocalDateTime

data class OptionResponse(
        val id: Long,
        val questionId: Long,
        val text: String,
        val active: Boolean,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime

)