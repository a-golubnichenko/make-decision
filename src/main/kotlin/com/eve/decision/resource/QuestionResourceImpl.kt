package com.eve.decision.resource

import com.eve.decision.dto.OptionResponse
import com.eve.decision.dto.SaveQuestionRequest
import com.eve.decision.dto.QuestionResponse
import com.eve.decision.dto.SaveOptionRequest
import com.eve.decision.resource.QuestionResourceImpl.Companion.BASE_QUESTION_RESOURCE_URL
import com.eve.decision.service.QuestionManagementService
import com.eve.decision.service.auth.UserDetailsImpl
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = [BASE_QUESTION_RESOURCE_URL])
class QuestionResourceImpl(
        private val questionManagementService: QuestionManagementService
) : QuestionResource {

    @GetMapping
    override fun findQuestionsByUserId(
            authentication: Authentication,
            pageable: Pageable
    ): ResponseEntity<Page<QuestionResponse?>> {
        val userId: Long = (authentication.principal as UserDetailsImpl).id
        return ResponseEntity.ok(this.questionManagementService.findQuestionsByUser(userId, pageable))
    }

    @PostMapping
    override fun addQuestion(
            authentication: Authentication,
            @RequestBody saveQuestionRequest: SaveQuestionRequest
    ): ResponseEntity<QuestionResponse> {
        val userId: Long = (authentication.principal as UserDetailsImpl).id
        return ResponseEntity.ok(this.questionManagementService.saveQuestion(userId, saveQuestionRequest))
    }

    @PutMapping("/{id}")
    override fun updateQuestion(
            authentication: Authentication,
            @PathVariable id: Long,
            @RequestBody saveQuestionRequest: SaveQuestionRequest
    ): ResponseEntity<QuestionResponse> {
        val userId: Long = (authentication.principal as UserDetailsImpl).id
        return ResponseEntity.ok(this.questionManagementService.updateQuestion(userId, id, saveQuestionRequest))
    }

    @DeleteMapping("/{id}")
    override fun deleteQuestion(authentication: Authentication, @PathVariable id: Long): ResponseEntity<Unit> {
        this.questionManagementService.deleteQuestionById(id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/{questionId}/option")
    override fun addOption(
            authentication: Authentication,
            @PathVariable questionId: Long,
            @RequestBody saveOptionRequest: SaveOptionRequest
    ): ResponseEntity<OptionResponse> {
        val userId: Long = (authentication.principal as UserDetailsImpl).id
        return ResponseEntity.ok(this.questionManagementService.addOption(userId, questionId, saveOptionRequest))

    }

    @PutMapping("/{questionId}/option/{optionId}")
    override fun updateOption(
            authentication: Authentication,
            @PathVariable questionId: Long,
            @PathVariable optionId: Long,
            @RequestBody saveOptionRequest: SaveOptionRequest
    ): ResponseEntity<OptionResponse> {
        val userId: Long = (authentication.principal as UserDetailsImpl).id
        return ResponseEntity.ok(this.questionManagementService.updateOption(userId, questionId, optionId, saveOptionRequest))
    }

    @DeleteMapping("/{questionId}/option/{optionId}")
    override fun deleteOption(
            authentication: Authentication,
            @PathVariable questionId: Long,
            @PathVariable optionId: Long
    ): ResponseEntity<Unit> {
        val userId: Long = (authentication.principal as UserDetailsImpl).id
        this.questionManagementService.deleteOptionById(userId, questionId, optionId)
        return ResponseEntity.noContent().build()
    }

    companion object {
        const val BASE_QUESTION_RESOURCE_URL: String = "v1/question"
    }

}