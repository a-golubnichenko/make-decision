package com.eve.decision.service.auth

import com.eve.decision.model.ERole
import com.eve.decision.model.User
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(
        var id: Long,
        var userName: String,
        var email: String,
        @JsonIgnore var pass: String,
        authorities: MutableCollection<GrantedAuthority>
) : UserDetails {

    private val grantedAuthorities: MutableCollection<out GrantedAuthority> = authorities

    companion object {

        fun build(user: User): UserDetailsImpl {
            val simpleGrantedAuthorities: MutableSet<GrantedAuthority> = user.roles
                    .map { SimpleGrantedAuthority(it.name?.name) }
                    .toMutableSet()
            return UserDetailsImpl(user.id, user.username, user.email, user.password, simpleGrantedAuthorities)
        }

    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return grantedAuthorities
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getUsername(): String {
        return userName
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun getPassword(): String {
        return pass
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    fun isAdmin() = authorities.any { it.authority.equals(ERole.ROLE_ADMIN.name) }

}