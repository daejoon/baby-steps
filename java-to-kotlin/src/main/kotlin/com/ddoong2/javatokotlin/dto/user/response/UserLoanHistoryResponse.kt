package com.ddoong2.javatokotlin.dto.user.response

import com.ddoong2.javatokotlin.domain.user.User
import com.ddoong2.javatokotlin.domain.user.loanhistory.UserLoanHistory
import com.ddoong2.javatokotlin.domain.user.loanhistory.UserLoanStatus

data class UserLoanHistoryResponse(
    val name: String,
    val books: List<BookHistoryResponse>,
) {
    companion object {
        fun of(user: User): UserLoanHistoryResponse {
            return UserLoanHistoryResponse(
                name = user.name,
                books = user.userLoanHistories.map(BookHistoryResponse::of)
            )
        }
    }
}

data class BookHistoryResponse(
    val name: String,
    val isReturn: Boolean,
) {
    companion object {
        fun of(history: UserLoanHistory): BookHistoryResponse {
            return BookHistoryResponse(
                name = history.bookName,
                isReturn = history.isReturn
            )
        }
    }

}