package com.eve.decision.config.jwt

import com.eve.decision.service.auth.UserDetailsImpl
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.Key
import java.util.*


@Component
class JwtUtils {

    val logger: Logger = LoggerFactory.getLogger(this::class.java.simpleName)

    @Value("\${app.jwtSecret}")
    val jwtSecret: String? = null

    @Value("\${app.jwtExpirationMs}")
    val jwtExpirationMs: Long? = null

    var key: Key? = null;

    fun generateJwtToken(authentication: Authentication): String? {
        val userPrincipal = authentication.principal as UserDetailsImpl
        return Jwts.builder().setSubject(userPrincipal.username).setIssuedAt(Date())
                .setExpiration(Date(Date().time + this.jwtExpirationMs!!))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512).compact()
    }

    fun validateJwtToken(jwt: String?): Boolean {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(jwt)
            return true
        } catch (e: MalformedJwtException) {
            logger.error("Can not validate token {}", e.message, e)
        } catch (e: IllegalArgumentException) {
            logger.error("Can not validate token {}", e.message, e)
        }
        return false
    }

    fun getUserNameFromJwtToken(jwt: String?): String? {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwt).body.subject
    }

    private fun getSigningKey(): Key? {
        if (key == null) {
            val keyBytes: ByteArray = jwtSecret!!.toByteArray(StandardCharsets.UTF_8)
            key = Keys.hmacShaKeyFor(keyBytes)
        }
        return key;
    }

}