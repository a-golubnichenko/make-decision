package com.eve.decision.transformer

import com.eve.decision.dto.OptionResponse
import com.eve.decision.dto.QuestionResponse
import com.eve.decision.model.Option
import com.eve.decision.model.Question
import java.time.LocalDateTime

fun Option?.toOptionResponse(): OptionResponse {

    return OptionResponse(
            id = this?.id ?: 0L,
            questionId = this?.question?.id ?: 0L,
            text = this?.text ?: "",
            active = this?.active ?: true,
            createdAt = this?.createdAt ?: LocalDateTime.now(),
            updatedAt = this?.updatedAt ?: LocalDateTime.now(),
    )

}