package com.eve.decision.resource

import java.time.LocalDateTime

data class ErrorResponse(
        val title: String = "Bad request",
        val message: String,
        val dateTime: LocalDateTime = LocalDateTime.now()

)