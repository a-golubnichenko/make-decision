package com.eve.decision.service

import com.eve.decision.config.jwt.JwtUtils
import com.eve.decision.dao.RoleDao
import com.eve.decision.dao.UserDao
import com.eve.decision.dto.AddUserRequest
import com.eve.decision.dto.JwtResponse
import com.eve.decision.dto.LoginUserRequest
import com.eve.decision.dto.UpdateUserRequest
import com.eve.decision.dto.UserResponse
import com.eve.decision.model.ERole
import com.eve.decision.model.Role
import com.eve.decision.model.User
import com.eve.decision.service.auth.UserDetailsImpl
import com.eve.decision.transformer.AddUserRequestTransformer
import com.eve.decision.transformer.toUserResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.stream.Collectors


@Service
class UserManagementServiceImpl(
        private val userDao: UserDao,
        private val roleDao: RoleDao,
        private val authenticationManager: AuthenticationManager,
        private val jwtUtils: JwtUtils,
        private val addUserRequestTransformer: AddUserRequestTransformer
) : UserManagementService {

    override fun findById(id: Long): UserResponse? = this.findUserById(id).toUserResponse()

    override fun findAll(pageable: Pageable): Page<UserResponse> =
            this.userDao.findAll(pageable).map(User::toUserResponse)

    override fun save(addUserRequest: AddUserRequest): UserResponse {
        val user = addUserRequestTransformer.transform(addUserRequest)
        user.roles = transformToEntityRoles(addUserRequest.roles).toSet()
        return this.saveOrUpdate(user)
    }

    override fun update(updateUserRequest: UpdateUserRequest): UserResponse {
        val user = this.findUserById(updateUserRequest.id)
                ?: throw IllegalStateException("${updateUserRequest.id} not found")
        return this.saveOrUpdate(user.apply {
            this.username = updateUserRequest.username
            this.firstName = updateUserRequest.firstName
            this.lastName = updateUserRequest.lastName
            this.email = updateUserRequest.email
            this.updatedAt = LocalDateTime.now()
            this.roles = transformToEntityRoles(updateUserRequest.roles).toSet()
        })
    }

    override fun deleteById(id: Long) {
        this.userDao.deleteById(id)
    }

    override fun login(loginUserRequest: LoginUserRequest): JwtResponse {
        val authentication: Authentication = authenticationManager
                .authenticate(UsernamePasswordAuthenticationToken(loginUserRequest.username, loginUserRequest.password))

        SecurityContextHolder.getContext().authentication = authentication
        val jwt: String = jwtUtils.generateJwtToken(authentication) ?: ""

        val userDetails = authentication.principal as UserDetailsImpl
        val roles = userDetails.authorities.stream()
                .map { item: GrantedAuthority? -> item!!.authority }
                .collect(Collectors.toSet())

        return JwtResponse(jwt,
                userDetails.id,
                userDetails.userName,
                userDetails.email,
                roles)
    }

    private fun findUserById(id: Long): User? = this.userDao.findByIdOrNull(id)

    private fun saveOrUpdate(user: User): UserResponse = this.userDao.save(user).toUserResponse()

    private fun transformToEntityRoles(roles: Collection<String>): Collection<Role> {
        val result = mutableSetOf<Role>()
        for (role in roles) {
            val entityRole = roleDao.findByName(ERole.valueOf(role))
            if (entityRole != null) {
                result.add(entityRole)
            }
        }
        if (result.isEmpty()) {
            roleDao.findByName(ERole.ROLE_USER)?.let { result.add(it) }
        }
        return result
    }

}