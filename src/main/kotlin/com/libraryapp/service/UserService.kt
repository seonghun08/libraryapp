package com.libraryapp.service

import com.libraryapp.domain.user.UserRepository
import com.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.libraryapp.dto.request.UserCreateRequest
import com.libraryapp.dto.request.UserUpdateRequest
import com.libraryapp.dto.response.BookHistoryResponse
import com.libraryapp.dto.response.UserLoanHistoryResponse
import com.libraryapp.dto.response.UserResponse
import com.libraryapp.util.fail
import com.libraryapp.util.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository
) {
   @Transactional
   fun saveUser(request: UserCreateRequest): Long {
       return userRepository.save(request.toEntity()).id!!
   }

    @Transactional(readOnly = true)
    fun getAllUsers(): List<UserResponse> {
        return userRepository.findAll().map { UserResponse.of(it) }
    }

    @Transactional
    fun updateUserName(request: UserUpdateRequest) {
        val user = userRepository.findByIdOrThrow(request.id)
        user.updateName(request.name)
    }

    @Transactional
    fun deleteUser(userName: String) {
        val user = userRepository.findByName(userName) ?: fail()
        userRepository.delete(user)
    }

    @Transactional
    fun deleteUser(id: Long) {
        userRepository.deleteById(id)
    }

    @Transactional(readOnly = true)
    fun getUserLoanHistories(): List<UserLoanHistoryResponse> {
        return userRepository.findAllWithHistories()
            .map(UserLoanHistoryResponse::of)
    }
}