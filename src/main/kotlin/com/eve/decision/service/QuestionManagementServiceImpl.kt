package com.eve.decision.service

import com.eve.decision.dao.OptionDao
import com.eve.decision.dao.QuestionDao
import com.eve.decision.dto.OptionResponse
import com.eve.decision.dto.QuestionResponse
import com.eve.decision.dto.SaveOptionRequest
import com.eve.decision.dto.SaveQuestionRequest
import com.eve.decision.model.Option
import com.eve.decision.model.Question
import com.eve.decision.service.auth.UserDetailsImpl
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

    override fun findQuestionsByUser(userDetails: UserDetailsImpl, pageable: Pageable): Page<QuestionResponse?> =
            this.questionDao.findByUserId(userDetails.id, pageable).map(Question::toQuestionResponse)

    override fun saveQuestion(
            userDetails: UserDetailsImpl,
            saveQuestionRequest: SaveQuestionRequest
    ): QuestionResponse {
        val question = Question()
        question.userId = userDetails.id
        question.text = saveQuestionRequest.text
        return this.saveOrUpdateQuestion(question)
    }

    override fun updateQuestion(
            userDetails: UserDetailsImpl,
            questionId: Long,
            saveQuestionRequest: SaveQuestionRequest
    ): QuestionResponse {
        val question = findQuestionByIdOrNull(questionId) ?: throw IllegalStateException("$questionId not found")
        checkQuestionPermissions(question, userDetails)
        question.text = saveQuestionRequest.text
        question.userId = userDetails.id
        question.updatedAt = LocalDateTime.now()
        return this.saveOrUpdateQuestion(question)
    }

    override fun deleteQuestion(userDetails: UserDetailsImpl, questionId: Long) {
        val question = findQuestionByIdOrNull(questionId) ?: throw IllegalStateException("$questionId not found")
        checkQuestionPermissions(question, userDetails)
        this.questionDao.deleteById(questionId)
    }

    override fun addOption(
            userDetails: UserDetailsImpl,
            questionId: Long,
            optionRequest: SaveOptionRequest
    ): OptionResponse {
        if (optionRequest.text == null) {
            throw IllegalStateException("Option is empty")
        }
        val question = findQuestionByIdOrNull(questionId) ?: throw IllegalStateException("$questionId not found")
        checkQuestionPermissions(question, userDetails)
        val option = Option()
        option.question = question
        option.text = optionRequest.text
        option.active = optionRequest.active ?: true
        question.options.add(option)
        return this.saveOrUpdateOption(option)
    }

    override fun updateOption(
            userDetails: UserDetailsImpl,
            questionId: Long,
            optionId: Long,
            optionRequest: SaveOptionRequest
    ): OptionResponse {
        val option = findOptionByIdOrNull(optionId) ?: throw IllegalStateException("$optionId not found")
        checkQuestionPermissions(option.question, userDetails)
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

    override fun deleteOption(userDetails: UserDetailsImpl, questionId: Long, optionId: Long) {
        val option = findOptionByIdOrNull(optionId) ?: throw IllegalStateException("$optionId not found")
        checkQuestionPermissions(option.question, userDetails)
        checkOptionPermissions(option, questionId)

        this.optionDao.deleteById(optionId)
    }

    private fun findQuestionByIdOrNull(id: Long): Question? = this.questionDao.findByIdOrNull(id)

    private fun saveOrUpdateQuestion(question: Question): QuestionResponse = this.questionDao.save(question).toQuestionResponse()

    private fun findOptionByIdOrNull(id: Long): Option? = this.optionDao.findByIdOrNull(id)

    private fun saveOrUpdateOption(option: Option): OptionResponse = this.optionDao.save(option).toOptionResponse()

    private fun checkQuestionPermissions(question: Question, userDetails: UserDetailsImpl) {
        if (question.userId != userDetails.id && !userDetails.isAdmin()) {
            throw IllegalStateException("You are not author of this question")
        }
    }

    private fun checkOptionPermissions(option: Option, questionId: Long) {
        if (option.question.id != questionId) {
            throw IllegalStateException("It is not an option of this question")
        }
    }

}