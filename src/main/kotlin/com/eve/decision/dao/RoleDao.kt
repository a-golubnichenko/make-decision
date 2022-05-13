package com.eve.decision.dao

import com.eve.decision.model.ERole
import com.eve.decision.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleDao: JpaRepository<Role, Long> {

    fun findByName(name: ERole): Role?

}