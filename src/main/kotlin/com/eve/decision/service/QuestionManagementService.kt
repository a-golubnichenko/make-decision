package com.eve.decision.service

import com.eve.decision.dto.AddQuestionRequest
import com.eve.decision.dto.QuestionResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface QuestionManagementService {

    fun findById(id: Long): QuestionResponse?
    fun findQuestionsByUser(userId: Long, pageable: Pageable): Page<QuestionResponse?>
    fun save(userId: Long, addUQuestionRequest: AddQuestionRequest): QuestionResponse
    //fun update(updateUserRequest: UpdateUserRequest): UserResponse
    //fun deleteById(id: Long)

}