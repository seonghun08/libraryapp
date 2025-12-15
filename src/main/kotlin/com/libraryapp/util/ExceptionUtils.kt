package com.libraryapp.util

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull

fun fail(): Nothing {
    throw IllegalArgumentException()
}

fun <T, ID : Any> CrudRepository<T, ID>.findByIdOrThrow(id: ID): T = this.findByIdOrNull(id) ?: fail()
