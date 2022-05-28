package com.eve.decision.service

import com.eve.decision.dao.OptionDao
import com.eve.decision.dao.QuestionDao
import com.eve.decision.dto.OptionResponse
import com.eve.decision.dto.QuestionResponse
import com.eve.decision.dto.SaveOptionRequest
import com.eve.decision.dto.SaveQuestionRequest
import com.eve.decision.model.Option
import com.eve.decision.model.Question
import com.eve.decision.transformer.toOptionResponse
import com.eve.decision.transformer.toQuestionResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class QuestionManagementServiceImpl(
        private val questionDao: QuestionDao,
        private val optionDao: OptionDao
) : QuestionManagementService {

    override fun findQuestion(id: Long): QuestionResponse? = this.findQuestionByIdOrNull(id).toQuestionResponse()

    override fun findQuestionsByUser(userId: Long, pageable: Pageable): Page<QuestionResponse?> =
            this.questionDao.findByUserId(userId, pageable).map(Question::toQuestionResponse)

    override fun saveQuestion(
            userId: Long,
            saveQuestionRequest: SaveQuestionRequest
    ): QuestionResponse {
        val question = Question()
        question.userId = userId
        question.text = saveQuestionRequest.text
        return this.saveOrUpdateQuestion(question)
    }

    override fun updateQuestion(
            userId: Long,
            questionId: Long,
            saveQuestionRequest: SaveQuestionRequest
    ): QuestionResponse {
        val question = findQuestionByIdOrNull(questionId) ?: throw IllegalStateException("$questionId not found")
        checkQuestionPermissions(question, userId)
        question.text = saveQuestionRequest.text
        question.userId = userId
        question.updatedAt = LocalDateTime.now()
        return this.saveOrUpdateQuestion(question)
    }

    override fun deleteQuestion(userId: Long, questionId: Long) {
        val question = findQuestionByIdOrNull(questionId) ?: throw IllegalStateException("$questionId not found")
        checkQuestionPermissions(question, userId)
        this.questionDao.deleteById(questionId)
    }

    override fun addOption(
            userId: Long,
            questionId: Long,
            optionRequest: SaveOptionRequest
    ): OptionResponse {
        if (optionRequest.text == null) {
            throw IllegalStateException("Option is empty")
        }
        val question = findQuestionByIdOrNull(questionId) ?: throw IllegalStateException("$questionId not found")
        checkQuestionPermissions(question, userId)
        val option = Option()
        option.question = question
        option.text = optionRequest.text
        option.active = optionRequest.active ?: true
        question.options.add(option)
        return this.saveOrUpdateOption(option)
    }

    override fun updateOption(
            userId: Long,
            questionId: Long,
            optionId: Long,
            optionRequest: SaveOptionRequest
    ): OptionResponse {
        val option = findOptionByIdOrNull(optionId) ?: throw IllegalStateException("$optionId not found")
        checkQuestionPermissions(option.question, userId)
        checkOptionPermissions(option, questionId)
        if (optionRequest.text != null) {
            option.text = optionRequest.text
        }
        if (optionRequest.active != null) {
            option.active = optionRequest.active
        }
        option.updatedAt = LocalDateTime.now()
        return this.saveOrUpdateOption(option)
    }

    override fun deleteOption(userId: Long, questionId: Long, optionId: Long) {
        val option = findOptionByIdOrNull(optionId) ?: throw IllegalStateException("$optionId not found")
        checkQuestionPermissions(option.question, userId)
        checkOptionPermissions(option, questionId)

        this.optionDao.deleteById(optionId)
    }

    private fun findQuestionByIdOrNull(id: Long): Question? = this.questionDao.findByIdOrNull(id)

    private fun saveOrUpdateQuestion(question: Question): QuestionResponse = this.questionDao.save(question).toQuestionResponse()

    private fun findOptionByIdOrNull(id: Long): Option? = this.optionDao.findByIdOrNull(id)

    private fun saveOrUpdateOption(option: Option): OptionResponse = this.optionDao.save(option).toOptionResponse()

    private fun checkQuestionPermissions(question: Question, userId: Long) {
        if (question.userId != userId) {
            throw IllegalStateException("You are not author of this question")
        }
    }

    private fun checkOptionPermissions(option: Option, questionId: Long) {
        if (option.question.id != questionId) {
            throw IllegalStateException("It is not an option of this question")
        }
    }

}