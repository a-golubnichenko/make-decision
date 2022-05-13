package com.eve.decision.resource

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/noop")
class NoOpResource {

    @GetMapping
    fun success(): ResponseEntity<String> {
        return ResponseEntity.ok().build()
    }

}