package com.libraryapp.service

import com.libraryapp.domain.user.UserRepository
import com.libraryapp.domain.user.Users
import com.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.libraryapp.domain.user.loanhistory.LoanBookHistoryRepository
import com.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.libraryapp.dto.request.UserCreateRequest
import com.libraryapp.dto.request.UserUpdateRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UsersServiceTest @Autowired constructor(
    private val userRepository: UserRepository,
    private val userService: UserService,
    private val loanBookHistoryRepository: LoanBookHistoryRepository
) {
    @AfterEach
    fun clean() {
        println("========== CLEAN ==========")
        userRepository.deleteAll()
    }

    @Test
    @DisplayName("유저 저장 정상")
    fun saveUser() {
        // given
        val name = "kotlin"
        val request = UserCreateRequest(name, null)

        // when
        userService.saveUser(request)

        // then
        val results = userRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo(name)
        assertThat(results[0]?.age).isNull()
    }

    @Test
    @DisplayName("유저 전체 조회 정상")
    fun getAllUsers() {
        // given
        val list = listOf(
            Users("A", 20),
            Users("B", null),
            Users("C", 15)
        )
        userRepository.saveAll(list)

        // when
        val results = userService.getAllUsers()

        // then
        assertThat(results).hasSize(3)
        assertThat(results).extracting("name").containsExactlyInAnyOrder("A", "B", "C")
        assertThat(results).extracting("age").containsExactlyInAnyOrder(20, null, 15)
    }

    @Test
    @DisplayName("유저 이름 수정 정상")
    fun updateUserName() {
        // given
        val users = userRepository.save(Users("A", 20))
        val request = UserUpdateRequest(users.id!!, "B")

        // when
        userService.updateUserName(request)

        // then
        val isNull = userRepository.findByName("A")
        val result = userRepository.findById(users.id)

        assertThat(isNull).isNull()
        assertThat(result).isNotNull()
    }

    @Test
    @DisplayName("유저 삭제 정상")
    fun deleteUser() {
        // given
        userRepository.save(Users("A", null))

        // when
        userService.deleteUser("A")

        // then
        assertThat(userRepository.findAll()).isEmpty()
    }

    @Test
    @DisplayName("대출 기록이 없는 유저도 응답에 포함된다.")
    fun getUserLoanHistoriesT1() {
        // given
        val name = "A"
        userRepository.save(Users(name, null))

        // when
        val result = userService.getUserLoanHistories()

        // then
        assertThat(result).hasSize(1)
        assertThat(result[0].name).isEqualTo(name)
        assertThat(result[0].books).isEmpty()
    }

    @Test
    @DisplayName("대출 기록이 많은 유저의 응답이 정상 작동한다.")
    fun getUserLoanHistoriesT2() {
        // given
        val users = userRepository.save(Users("A", null))

        loanBookHistoryRepository.saveAll(listOf(
            UserLoanHistory.fixture(users, "book1", UserLoanStatus.LOANED),
            UserLoanHistory.fixture(users, "book2", UserLoanStatus.LOANED),
            UserLoanHistory.fixture(users, "book3", UserLoanStatus.RETURNED)
        ))

        // when
        val result = userService.getUserLoanHistories()

        // then
        assertThat(result).hasSize(1)
        assertThat(result[0].name).isEqualTo("A")
        assertThat(result[0].books).hasSize(3)
        assertThat(result[0].books).extracting("name")
            .containsExactlyInAnyOrder("book1", "book2", "book3")
        assertThat(result[0].books).extracting("isReturn")
            .containsExactlyInAnyOrder(false, false, true)
    }
}
