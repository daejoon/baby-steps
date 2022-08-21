package com.ddoong2.javatokotlin.domain.book

import com.ddoong2.javatokotlin.dto.book.response.BookStatResponse
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BookRepository : JpaRepository<Book, Long> {

    fun findByName(bookName: String) : Book?

    @Query("SELECT NEW com.ddoong2.javatokotlin.dto.book.response.BookStatResponse(b.type, count(b.id)) " +
            " FROM Book b GROUP BY b.type")
    fun getStats(): List<BookStatResponse>

}