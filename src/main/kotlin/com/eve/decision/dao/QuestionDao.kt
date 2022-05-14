package com.eve.decision.dao

import com.eve.decision.model.Question
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QuestionDao: JpaRepository<Question, Long> {

    fun findByUserId(userId: Long): Collection<Question>

}