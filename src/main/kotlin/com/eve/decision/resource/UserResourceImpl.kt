package com.eve.decision.resource

import com.eve.decision.dto.JwtResponse
import com.eve.decision.dto.LoginUserRequest
import com.eve.decision.dto.UserResponse
import com.eve.decision.resource.UserResourceImpl.Companion.BASE_USER_RESOURCE_URL
import com.eve.decision.service.UserManagementService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = [BASE_USER_RESOURCE_URL])
class UserResourceImpl(
        private val userManagementService: UserManagementService
) : UserResource {

    @GetMapping("/{id}")
    override fun findById(@PathVariable id: Long): ResponseEntity<UserResponse?> {
        val userResponse = this.userManagementService.findById(id)
        return ResponseEntity.ok(userResponse)
    }

    @GetMapping
    override fun findAll(pageable: Pageable): ResponseEntity<Page<UserResponse>> {
        return ResponseEntity.ok(this.userManagementService.findAll(pageable))
    }

    @PostMapping("login")
    override fun login(@RequestBody loginUserRequest: LoginUserRequest): ResponseEntity<JwtResponse> {
        return ResponseEntity.ok(this.userManagementService.login(loginUserRequest))
    }

    companion object {
        const val BASE_USER_RESOURCE_URL: String = "v1/user"
    }

}