package com.ddoong2.javatokotlin.dto.book.response

import com.ddoong2.javatokotlin.domain.book.BookType

data class BookStatResponse(
    val type: BookType,
    val count: Long,
) {
}
