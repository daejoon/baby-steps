package com.ddoong2.javatokotlin.dto.book.response

import com.ddoong2.javatokotlin.domain.book.BookType

data class BookStatResponse(
    val type: BookType,
    var count: Int,
) {

    fun plusOne() {
        count++
    }
}
