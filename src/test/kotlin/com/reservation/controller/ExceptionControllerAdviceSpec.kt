package com.reservation.controller

import com.reservation.exception.BadRequestException
import com.reservation.exception.NotFoundException
import com.reservation.exception.ReservationException
import com.reservation.helper.TestHelper
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class ExceptionControllerAdviceSpec {

    @Mock
    private lateinit var exceptionControllerAdvice: ExceptionControllerAdvice

    @Before
    fun setup() {
        exceptionControllerAdvice = ExceptionControllerAdvice()
    }

    @Test
    fun test_handleBadRequestException() {
        val actual = exceptionControllerAdvice.handleBadRequestException(BadRequestException("mock exception"))
        val actualBody = actual.body

        Assertions.assertThat(actual.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
        actualBody?.let {
            Assertions.assertThat(it.status).isEqualTo(400)
            Assertions.assertThat(it.message).isEqualTo("mock exception")
        }
    }

    @Test
    fun test_handleMethodArgumentNotValidException() {
        val mockException = mock(MethodArgumentNotValidException::class.java)
        Mockito.`when`(mockException.message).thenReturn("mock exception")

        val actual = exceptionControllerAdvice.handleMethodArgumentNotValidException(mockException)
        val actualBody = actual.body

        Assertions.assertThat(actual.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
        actualBody?.let {
            Assertions.assertThat(it.status).isEqualTo(400)
            Assertions.assertThat(it.message).isEqualTo("mock exception")
        }
    }

    @Test
    fun test_handleNotFoundException() {
        val actual = exceptionControllerAdvice.handleNotFoundException(NotFoundException("mock exception"))
        val actualBody = actual.body

        Assertions.assertThat(actual.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
        actualBody?.let {
            Assertions.assertThat(it.status).isEqualTo(404)
            Assertions.assertThat(it.message).isEqualTo("mock exception")
        }
    }

    @Test
    fun test_handleReservationException() {
        val actual = exceptionControllerAdvice.handleReservationException(ReservationException("mock exception"))
        val actualBody = actual.body

        Assertions.assertThat(actual.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
        actualBody?.let {
            Assertions.assertThat(it.status).isEqualTo(400)
            Assertions.assertThat(it.message).isEqualTo("mock exception")
        }
    }
}