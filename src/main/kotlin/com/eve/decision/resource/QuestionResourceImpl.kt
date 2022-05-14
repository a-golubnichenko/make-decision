package com.eve.decision.resource

import com.eve.decision.dto.AddQuestionRequest
import com.eve.decision.dto.QuestionResponse
import com.eve.decision.resource.QuestionResourceImpl.Companion.BASE_QUESTION_RESOURCE_URL
import com.eve.decision.service.QuestionManagementService
import com.eve.decision.service.auth.UserDetailsImpl
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = [BASE_QUESTION_RESOURCE_URL])
class QuestionResourceImpl(
        private val questionManagementService: QuestionManagementService
) : QuestionResource {

    @GetMapping
    override fun findByUserId(authentication: Authentication, pageable: Pageable): ResponseEntity<Page<QuestionResponse?>> {
        val userId: Long = (authentication.principal as UserDetailsImpl).id
        return ResponseEntity.ok(this.questionManagementService.findQuestionsByUser(userId, pageable))
    }

    @PostMapping
    override fun save(authentication: Authentication, @RequestBody addUQuestionRequest: AddQuestionRequest): ResponseEntity<QuestionResponse> {
        val userId: Long = (authentication.principal as UserDetailsImpl).id
        return ResponseEntity.ok(this.questionManagementService.save(userId, addUQuestionRequest))
    }

    companion object {
        const val BASE_QUESTION_RESOURCE_URL: String = "v1/question"
    }

}