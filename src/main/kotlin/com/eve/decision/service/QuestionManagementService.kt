package com.eve.decision.service

import com.eve.decision.dto.OptionResponse
import com.eve.decision.dto.SaveQuestionRequest
import com.eve.decision.dto.QuestionResponse
import com.eve.decision.dto.SaveOptionRequest
import com.eve.decision.service.auth.UserDetailsImpl
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface QuestionManagementService {

    fun findQuestion(id: Long): QuestionResponse?
    fun findQuestionsByUser(userDetails: UserDetailsImpl, pageable: Pageable): Page<QuestionResponse?>
    fun saveQuestion(userDetails: UserDetailsImpl, saveQuestionRequest: SaveQuestionRequest): QuestionResponse
    fun updateQuestion(userDetails: UserDetailsImpl, questionId: Long, saveQuestionRequest: SaveQuestionRequest): QuestionResponse
    fun deleteQuestion(userDetails: UserDetailsImpl, questionId: Long)

    fun addOption(userDetails: UserDetailsImpl, questionId: Long, optionRequest: SaveOptionRequest): OptionResponse
    fun updateOption(userDetails: UserDetailsImpl, questionId: Long, optionId: Long, optionRequest: SaveOptionRequest): OptionResponse
    fun deleteOption(userDetails: UserDetailsImpl, questionId: Long, optionId: Long)
}