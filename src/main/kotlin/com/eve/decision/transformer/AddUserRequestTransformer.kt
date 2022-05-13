package com.eve.decision.transformer

import com.eve.decision.dto.AddUserRequest
import com.eve.decision.model.User
import org.springframework.stereotype.Component

@Component
class AddUserRequestTransformer : Transformer<AddUserRequest, User> {

    override fun transform(source: AddUserRequest): User {
        val user = User()
        user.username = source.username
        user.password = source.password
        user.firstName = source.firstName
        user.lastName = source.lastName
        user.email = source.email
        return user
    }

}