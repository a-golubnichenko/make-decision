package com.eve.decision.transformer

import com.eve.decision.dto.QuestionResponse
import com.eve.decision.model.Question
import java.time.LocalDateTime

fun Question?.toQuestionResponse(): QuestionResponse {

    return QuestionResponse(
            id = this?.id ?: 1L,
            userId = this?.userId ?: 1L,
            text = this?.text ?: "",
            createdAt = this?.createdAt ?: LocalDateTime.now(),
            updatedAt = this?.updatedAt ?: LocalDateTime.now(),
    )

}