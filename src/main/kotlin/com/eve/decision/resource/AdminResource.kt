package com.eve.decision.resource

import com.eve.decision.dto.AddUserRequest
import com.eve.decision.dto.UpdateUserRequest
import com.eve.decision.dto.UserResponse
import org.springframework.http.ResponseEntity

interface AdminResource {

    fun save(addUserRequest: AddUserRequest): ResponseEntity<UserResponse>
    fun update(updateUserRequest: UpdateUserRequest): ResponseEntity<UserResponse>
    fun deleteById(id:Long): ResponseEntity<Unit>

}