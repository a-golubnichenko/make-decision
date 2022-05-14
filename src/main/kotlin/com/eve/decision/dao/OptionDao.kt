package com.eve.decision.dao

import com.eve.decision.model.Option
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OptionDao: JpaRepository<Option, Long> {

    fun findByQuestionId(questionId: Long): Collection<Option>

}