package com.ddoong2.javatokotlin.controller.book

import com.ddoong2.javatokotlin.dto.book.request.BookLoanRequest
import com.ddoong2.javatokotlin.dto.book.request.BookRequest
import com.ddoong2.javatokotlin.dto.book.request.BookReturnRequest
import com.ddoong2.javatokotlin.dto.book.response.BookStatResponse
import com.ddoong2.javatokotlin.service.book.BookService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class BookController(
    private val bookService: BookService,
) {

    @PostMapping("/book")
    fun saveBook(@RequestBody request: BookRequest) {
        bookService.saveBook(request)
    }

    @PostMapping("/book/loan")
    fun loanBook(@RequestBody request: BookLoanRequest) {
        bookService.loanBook(request)
    }

    @PutMapping("/book/return")
    fun returnBook(@RequestBody request: BookReturnRequest) {
        bookService.returnBook(request)
    }

    @GetMapping("/book/loan")
    fun countLoanedBook(): Int {
        return bookService.countLoanedBooK()
    }

    @GetMapping("/book/stat")
    fun getBookStatistics(): List<BookStatResponse> {
        return bookService.getBookStatistics()
    }
}