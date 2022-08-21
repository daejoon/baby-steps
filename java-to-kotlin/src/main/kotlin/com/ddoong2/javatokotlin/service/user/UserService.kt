package com.ddoong2.javatokotlin.service.user

import com.ddoong2.javatokotlin.domain.user.User
import com.ddoong2.javatokotlin.domain.user.UserRepository
import com.ddoong2.javatokotlin.domain.user.loanhistory.UserLoanStatus
import com.ddoong2.javatokotlin.dto.user.request.UserCreateRequest
import com.ddoong2.javatokotlin.dto.user.request.UserUpdateRequest
import com.ddoong2.javatokotlin.dto.user.response.BookHistoryResponse
import com.ddoong2.javatokotlin.dto.user.response.UserLoanHistoryResponse
import com.ddoong2.javatokotlin.dto.user.response.UserResponse
import com.ddoong2.javatokotlin.util.fail
import com.ddoong2.javatokotlin.util.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
) {

    @Transactional
    fun saveUser(request: UserCreateRequest) {
        val newUser = User(request.name, request.age)
        userRepository.save(newUser)
    }

    @Transactional(readOnly = true)
    fun getUsers(): List<UserResponse> {
        return userRepository.findAll()
            .map { user -> UserResponse.of(user) }
    }

    @Transactional
    fun updateUserName(request: UserUpdateRequest) {
        val user = userRepository.findByIdOrThrow(request.id)

        user.updateName(request.name)
    }

    @Transactional
    fun deleteUser(name: String) {
        val user = userRepository.findByName(name) ?: fail()
        userRepository.delete(user)
    }

    @Transactional(readOnly = true)
    fun getUserLoanHistories(): List<UserLoanHistoryResponse> {
        return userRepository.findAllWithHistories().map { user ->
            UserLoanHistoryResponse(
                name = user.name,
                books = user.userLoanHistories.map { history ->
                    BookHistoryResponse(
                        name = history.bookName,
                        isReturn = history.status == UserLoanStatus.RETURNED
                    )
                }
            )
        }
    }


}