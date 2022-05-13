package com.eve.decision.service

import com.eve.decision.dto.AddUserRequest
import com.eve.decision.dto.JwtResponse
import com.eve.decision.dto.LoginUserRequest
import com.eve.decision.dto.UpdateUserRequest
import com.eve.decision.dto.UserResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserManagementService {

    fun findById(id: Long): UserResponse?
    fun findAll(pageable: Pageable): Page<UserResponse>
    fun save(addUserRequest: AddUserRequest): UserResponse
    fun update(updateUserRequest: UpdateUserRequest): UserResponse
    fun deleteById(id: Long)
    fun login(loginUserRequest: LoginUserRequest): JwtResponse

}