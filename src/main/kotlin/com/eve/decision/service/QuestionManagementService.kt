package com.eve.decision.service

import com.eve.decision.dto.OptionResponse
import com.eve.decision.dto.SaveQuestionRequest
import com.eve.decision.dto.QuestionResponse
import com.eve.decision.dto.SaveOptionRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface QuestionManagementService {

    fun findQuestion(id: Long): QuestionResponse?
    fun findQuestionsByUser(userId: Long, pageable: Pageable): Page<QuestionResponse?>
    fun saveQuestion(userId: Long, saveQuestionRequest: SaveQuestionRequest): QuestionResponse
    fun updateQuestion(userId: Long, questionId: Long, saveQuestionRequest: SaveQuestionRequest): QuestionResponse
    fun deleteQuestion(userId: Long, questionId: Long)

    fun addOption(userId: Long, questionId: Long, optionRequest: SaveOptionRequest): OptionResponse
    fun updateOption(userId: Long, questionId: Long, optionId: Long, optionRequest: SaveOptionRequest): OptionResponse
    fun deleteOption(userId: Long, questionId: Long, optionId: Long)
}