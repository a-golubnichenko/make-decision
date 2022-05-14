package com.eve.decision.model

import java.time.LocalDateTime
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.OneToMany


@Entity(name = "questions")
class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    var userId: Long = 0;

    var text: String = ""

    var createdAt: LocalDateTime = LocalDateTime.now()

    var updatedAt: LocalDateTime = LocalDateTime.now()

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    private val options: Set<Option> = HashSet()

}