package com.libraryapp.config

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig(
    private val em: EntityManager
) {
    @Bean
    fun querydsl(): JPAQueryFactory {
        return JPAQueryFactory(em)
    }
}