package com.ddoong2.javatokotlin.repository.book

import com.ddoong2.javatokotlin.domain.book.QBook.book
import com.ddoong2.javatokotlin.dto.book.response.BookStatResponse
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

@Component
class BookQuerydslRepository(
    private val queryFactory: JPAQueryFactory,
) {

    fun getStats(): List<BookStatResponse> {
        return queryFactory
            .select(
                Projections.constructor(
                    BookStatResponse::class.java,
                    book.type,
                    book.id.count(),
                )
            )
            .from(book)
            .groupBy(book.type)
            .fetch()
    }

}