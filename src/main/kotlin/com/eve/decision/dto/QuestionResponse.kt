package com.eve.decision.dto

import java.time.LocalDateTime

data class QuestionResponse(
        val id: Long,
        val userId: Long,
        val text: String,
        val options: Collection<OptionResponse>,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime

)