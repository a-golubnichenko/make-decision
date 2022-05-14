package com.eve.decision.resource

import com.eve.decision.dto.AddQuestionRequest
import com.eve.decision.dto.QuestionResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication

interface QuestionResource {

    fun findByUserId(authentication: Authentication, pageable: Pageable): ResponseEntity<Page<QuestionResponse?>>
    fun save(authentication: Authentication, addUQuestionRequest: AddQuestionRequest): ResponseEntity<QuestionResponse>

}