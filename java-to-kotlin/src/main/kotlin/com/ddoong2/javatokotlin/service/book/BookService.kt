package com.ddoong2.javatokotlin.service.book

import com.ddoong2.javatokotlin.domain.book.Book
import com.ddoong2.javatokotlin.domain.book.BookRepository
import com.ddoong2.javatokotlin.domain.user.UserRepository
import com.ddoong2.javatokotlin.domain.user.loanhistory.UserLoanHistoryRepository
import com.ddoong2.javatokotlin.domain.user.loanhistory.UserLoanStatus
import com.ddoong2.javatokotlin.dto.book.request.BookLoanRequest
import com.ddoong2.javatokotlin.dto.book.request.BookRequest
import com.ddoong2.javatokotlin.dto.book.request.BookReturnRequest
import com.ddoong2.javatokotlin.dto.book.response.BookStatResponse
import com.ddoong2.javatokotlin.repository.book.BookQuerydslRepository
import com.ddoong2.javatokotlin.util.fail
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val bookQuerydslRepository: BookQuerydslRepository,
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository,
) {

    @Transactional
    fun saveBook(request: BookRequest) {
        val book = Book(request.name, request.type)
        bookRepository.save(book)
    }

    @Transactional
    fun loanBook(request: BookLoanRequest) {
        val book = bookRepository.findByName(request.bookName) ?: fail()

        if (userLoanHistoryRepository.findByBookNameAndStatus(request.bookName, UserLoanStatus.LOANED) != null) {
            throw IllegalArgumentException("진작 대출되어 있는 책 입니다")
        }

        val user = userRepository.findByName(request.userName) ?: fail()
        user.loanBook(book)
    }

    @Transactional
    fun returnBook(request: BookReturnRequest) {
        val user = userRepository.findByName(request.userName) ?: fail()
        user.returnBook(request.bookName)
    }

    @Transactional(readOnly = true)
    fun countLoanedBooK(): Int {
        return userLoanHistoryRepository.countByStatus(UserLoanStatus.LOANED).toInt()
    }

    @Transactional(readOnly = true)
    fun getBookStatistics(): List<BookStatResponse> {
        return bookQuerydslRepository.getStats()
    }

}