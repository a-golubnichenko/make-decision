package com.eve.decision.service

import com.eve.decision.dao.QuestionDao
import com.eve.decision.dto.QuestionResponse
import com.eve.decision.dto.SaveQuestionRequest
import com.eve.decision.model.Question
import com.eve.decision.transformer.toQuestionResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class QuestionManagementServiceImpl(
        private val questionDao: QuestionDao
) : QuestionManagementService {

    override fun findQuestionById(id: Long): QuestionResponse? = this.findQuestionByIdOrNull(id).toQuestionResponse()

    override fun findQuestionsByUser(userId: Long, pageable: Pageable): Page<QuestionResponse?> =
            this.questionDao.findByUserId(userId, pageable).map(Question::toQuestionResponse)

    override fun saveQuestion(
            userId: Long,
            saveQuestionRequest: SaveQuestionRequest
    ): QuestionResponse {
        val question = Question()
        question.userId = userId
        question.text = saveQuestionRequest.text
        return this.saveOrUpdate(question)
    }

    override fun updateQuestion(
            userId: Long,
            questionId: Long,
            saveQuestionRequest: SaveQuestionRequest
    ): QuestionResponse {
        val question = findQuestionByIdOrNull(questionId) ?: throw IllegalStateException("$questionId not found")
        question.userId = userId
        question.id = questionId
        question.text = saveQuestionRequest.text
        question.updatedAt = LocalDateTime.now();
        return this.saveOrUpdate(question)
    }

    override fun deleteQuestionById(id: Long) {
        this.questionDao.deleteById(id)
    }

    private fun findQuestionByIdOrNull(id: Long): Question? = this.questionDao.findByIdOrNull(id)

    private fun saveOrUpdate(question: Question): QuestionResponse = this.questionDao.save(question).toQuestionResponse()

}