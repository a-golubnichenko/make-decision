package com.eve.decision.config.jwt

import com.eve.decision.service.auth.UserDetailsImpl
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*


@Component
class JwtUtils {

    val logger: Logger = LoggerFactory.getLogger(this::class.java.simpleName)

    @Value("\${app.jwtSecret}")
    val jwtSecret: String? = null

    @Value("\${app.jwtExpirationMs}")
    val jwtExpirationMs: Long? = null

    fun generateJwtToken(authentication: Authentication): String? {
        val userPrincipal = authentication.principal as UserDetailsImpl
        return Jwts.builder().setSubject(userPrincipal.username).setIssuedAt(Date())
                .setExpiration(Date(Date().time + this.jwtExpirationMs!!))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact()
    }

    fun validateJwtToken(jwt: String?): Boolean {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt)
            return true
        } catch (e: MalformedJwtException) {
            logger.error("Can not validate token {}", e.message, e)
        } catch (e: IllegalArgumentException) {
            logger.error("Can not validate token {}", e.message, e)
        }
        return false
    }

    fun getUserNameFromJwtToken(jwt: String?): String? {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).body.subject
    }

}