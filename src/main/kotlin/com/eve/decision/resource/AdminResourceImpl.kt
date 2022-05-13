package com.eve.decision.resource

import com.eve.decision.dto.AddUserRequest
import com.eve.decision.dto.UpdateUserRequest
import com.eve.decision.dto.UserResponse
import com.eve.decision.resource.AdminResourceImpl.Companion.BASE_ADMIN_RESOURCE_URL
import com.eve.decision.resource.UserResourceImpl.Companion.BASE_USER_RESOURCE_URL
import com.eve.decision.service.UserManagementService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping(value = [BASE_ADMIN_RESOURCE_URL])
class AdminResourceImpl(
        private val userManagementService: UserManagementService
) : AdminResource {

    @PostMapping("register")
    override fun save(@RequestBody addUserRequest: AddUserRequest): ResponseEntity<UserResponse> {
        val userResponse = this.userManagementService.save(addUserRequest)
        return ResponseEntity
                .created(URI.create(BASE_USER_RESOURCE_URL.plus("/${userResponse.id}")))
                .body(userResponse)
    }

    @PutMapping
    override fun update(@RequestBody updateUserRequest: UpdateUserRequest): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(this.userManagementService.update(updateUserRequest))
    }

    @DeleteMapping("/{id}")
    override fun deleteById(@PathVariable id: Long): ResponseEntity<Unit> {
        this.userManagementService.deleteById(id)
        return ResponseEntity.noContent().build()
    }

    companion object {
        const val BASE_ADMIN_RESOURCE_URL: String = "v1/admin"
    }

}