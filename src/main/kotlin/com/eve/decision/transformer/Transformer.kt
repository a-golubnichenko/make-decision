package com.eve.decision.transformer

interface Transformer<A, B> {

    fun transform(source: A): B

}