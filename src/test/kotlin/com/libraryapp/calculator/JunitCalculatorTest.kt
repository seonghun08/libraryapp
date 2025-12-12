package com.libraryapp.calculator

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class JunitCalculatorTest {

    @Test
    fun add() {
        // given
        val calculator = Calculator(5)

        // when
        calculator.add(3)

        // then
        assertThat(calculator.number).isEqualTo(8)
    }

    @Test
    fun minus() {
        val calculator = Calculator(5)
        calculator.minus(3)
        assertThat(calculator.number).isEqualTo(2)
    }

    @Test
    fun multiply() {
        val calculator = Calculator(5)
        calculator.multiply(3)
        assertThat(calculator.number).isEqualTo(15)
    }

    @Test
    fun divide() {
        val calculator = Calculator(5)
        calculator.divide(3)
        assertThat(calculator.number).isEqualTo(1)
    }

    @Test
    fun divideFail() {
        val calculator = Calculator(5)
        // val message = assertThrows<IllegalArgumentException> {
        //     calculator.divide(0)
        // }.message
        // assertThat(message).isEqualTo("0으로 나눌 수 없습니다.")
        assertThrows<IllegalArgumentException> {
            calculator.divide(0)
        }.apply { // apply 함수 활용
            assertThat(this.message).isEqualTo("0으로 나눌 수 없습니다.")
        }
    }
}