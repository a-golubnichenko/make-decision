package com.eve.decision.service.auth

import com.eve.decision.dao.UserDao
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class UserDetailsServiceImpl(
        private val userDao: UserDao
) : UserDetailsService {

    @Transactional
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userDao.findByUsername(username)
                ?: throw UsernameNotFoundException("User with username '${username}' not found")
        return UserDetailsImpl.build(user)
    }

}