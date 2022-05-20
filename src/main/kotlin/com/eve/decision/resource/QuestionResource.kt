package com.eve.decision.resource

import com.eve.decision.dto.SaveQuestionRequest
import com.eve.decision.dto.QuestionResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication

interface QuestionResource {

    fun findQuestionsByUserId(authentication: Authentication, pageable: Pageable): ResponseEntity<Page<QuestionResponse?>>
    fun addQuestion(authentication: Authentication, saveQuestionRequest: SaveQuestionRequest): ResponseEntity<QuestionResponse>
    fun updateQuestion(authentication: Authentication, id: Long, saveQuestionRequest: SaveQuestionRequest): ResponseEntity<QuestionResponse>
    fun deleteQuestion(authentication: Authentication, id: Long): ResponseEntity<Unit>

}