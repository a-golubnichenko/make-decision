package com.eve.decision.resource

import com.eve.decision.dto.JwtResponse
import com.eve.decision.dto.LoginUserRequest
import com.eve.decision.dto.UserResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity

interface UserResource {

    fun findById(id: Long): ResponseEntity<UserResponse?>
    fun findAll(pageable: Pageable): ResponseEntity<Page<UserResponse>>
    fun login(loginUserRequest: LoginUserRequest): ResponseEntity<JwtResponse>

}