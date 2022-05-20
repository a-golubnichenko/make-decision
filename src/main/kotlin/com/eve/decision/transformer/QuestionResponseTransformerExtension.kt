package com.eve.decision.transformer

import com.eve.decision.dto.OptionResponse
import com.eve.decision.dto.QuestionResponse
import com.eve.decision.model.Option
import com.eve.decision.model.Question
import java.time.LocalDateTime
import java.util.*
import java.util.stream.Collectors

fun Question?.toQuestionResponse(): QuestionResponse {

    val options: MutableSet<OptionResponse> = this?.options?.stream()
            ?.map { item: Option? -> item!!.toOptionResponse() }
            ?.collect(Collectors.toSet()) ?: Collections.emptySet()

    return QuestionResponse(
            id = this?.id ?: 0L,
            userId = this?.userId ?: 0L,
            text = this?.text ?: "",
            options = options,
            createdAt = this?.createdAt ?: LocalDateTime.now(),
            updatedAt = this?.updatedAt ?: LocalDateTime.now(),
    )

}