package com.ddoong2.javatokotlin.dto.book.request

import com.ddoong2.javatokotlin.domain.book.BookType

data class BookRequest(
    val name: String,
    val type: BookType,
)