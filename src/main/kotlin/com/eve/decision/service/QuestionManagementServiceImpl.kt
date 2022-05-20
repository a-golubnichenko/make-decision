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
        return this.saveOrUpdateQuestion(question)
    }

    override fun updateQuestion(
            userId: Long,
            questionId: Long,
            saveQuestionRequest: SaveQuestionRequest
    ): QuestionResponse {
        val question = findQuestionByIdOrNull(questionId) ?: throw IllegalStateException("$questionId not found")
        question.text = saveQuestionRequest.text
        question.updatedAt = LocalDateTime.now()
        return this.saveOrUpdateQuestion(question)
    }

    override fun deleteQuestionById(id: Long) {
        this.questionDao.deleteById(id)
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
        if (question.userId != userId) {
            throw IllegalStateException("You are not author of this question")
        }
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
        if (option.question.userId != userId) {
            throw IllegalStateException("You are not author of this question")
        }
        if (option.question.id != questionId) {
            throw IllegalStateException("It is not an option of this question")
        }
        if (optionRequest.text != null) {
            option.text = optionRequest.text
        }
        if (optionRequest.active != null) {
            option.active = optionRequest.active
        }
        option.updatedAt = LocalDateTime.now()
        return this.saveOrUpdateOption(option)
    }

    private fun findQuestionByIdOrNull(id: Long): Question? = this.questionDao.findByIdOrNull(id)

    private fun saveOrUpdateQuestion(question: Question): QuestionResponse = this.questionDao.save(question).toQuestionResponse()

    private fun findOptionByIdOrNull(id: Long): Option? = this.optionDao.findByIdOrNull(id)

    private fun saveOrUpdateOption(option: Option): OptionResponse = this.optionDao.save(option).toOptionResponse()

}