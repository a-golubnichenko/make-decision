package com.eve.decision.transformer

import com.eve.decision.dto.UserResponse
import com.eve.decision.model.Role
import com.eve.decision.model.User
import java.time.LocalDateTime
import java.util.*
import java.util.stream.Collectors

fun User?.toUserResponse(): UserResponse {

    return UserResponse(
            id = this?.id ?: 1L,
            username = this?.username ?: "",
            firstName = this?.firstName ?: "",
            lastName = this?.lastName ?: "",
            email = this?.email ?: "",
            roles = this?.roles?.stream()
                    ?.map { item: Role? -> item!!.name?.name }
                    ?.collect(Collectors.toSet()) as Set<String>? ?: Collections.emptySet(),
            createdAt = this?.createdAt ?: LocalDateTime.now(),
            updatedAt = this?.updatedAt ?: LocalDateTime.now(),
    )

}