package com.eve.decision.service

import com.eve.decision.config.jwt.JwtUtils
import com.eve.decision.dao.QuestionDao
import com.eve.decision.dao.RoleDao
import com.eve.decision.dao.UserDao
import com.eve.decision.dto.AddQuestionRequest
import com.eve.decision.dto.QuestionResponse
import com.eve.decision.model.Question
import com.eve.decision.transformer.AddUserRequestTransformer
import com.eve.decision.transformer.toQuestionResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.stereotype.Service


@Service
class QuestionManagementServiceImpl(
        private val userDao: UserDao,
        private val roleDao: RoleDao,
        private val questionDao: QuestionDao,
        private val authenticationManager: AuthenticationManager,
        private val jwtUtils: JwtUtils,
        private val addUserRequestTransformer: AddUserRequestTransformer
) : QuestionManagementService {

    override fun findById(id: Long): QuestionResponse? = this.findQuestionById(id).toQuestionResponse()

    override fun findQuestionsByUser(userId: Long, pageable: Pageable): Page<QuestionResponse?> =
            this.questionDao.findByUserId(userId, pageable).map(Question::toQuestionResponse)

    override fun save(userId: Long, addUQuestionRequest: AddQuestionRequest): QuestionResponse {
        val question = Question()
        question.userId = userId;
        question.text = addUQuestionRequest.text
        return this.saveOrUpdate(question)
    }

//    override fun update(updateUserRequest: UpdateUserRequest): UserResponse {
//        val user = this.findUserById(updateUserRequest.id)
//                ?: throw IllegalStateException("${updateUserRequest.id} not found")
//        return this.saveOrUpdate(user.apply {
//            this.username = updateUserRequest.username
//            this.firstName = updateUserRequest.firstName
//            this.lastName = updateUserRequest.lastName
//            this.email = updateUserRequest.email
//            this.updatedAt = LocalDateTime.now()
//            this.roles = transformToEntityRoles(updateUserRequest.roles).toSet()
//        })
//    }

//    override fun deleteById(id: Long) {
//        this.userDao.deleteById(id)
//    }

    private fun findQuestionById(id: Long): Question? = this.questionDao.findByIdOrNull(id)

    private fun saveOrUpdate(question: Question): QuestionResponse = this.questionDao.save(question).toQuestionResponse()

}