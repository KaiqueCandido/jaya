package br.com.jaya.application.transaction

import br.com.jaya.domain.exception.ExchangeRateNotFoundException
import br.com.jaya.domain.exchangerate.ExchangeRate
import br.com.jaya.domain.exchangerate.ExchangeRateService
import br.com.jaya.domain.transaction.CurrencyType
import br.com.jaya.domain.transaction.Transaction
import br.com.jaya.domain.transaction.TransactionRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.math.BigDecimal
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime

class TransactionServiceImplTest {

    private val transactionRepository: TransactionRepository = mockk()
    private val exchangeRateService: ExchangeRateService = mockk()
    private val transactionService = TransactionServiceImpl(transactionRepository, exchangeRateService)

    @Test
    fun should_return_transaction_when_saved_with_success() {
        //given
        val createdAtMock = LocalDateTime.now()
        val transactionMock = Transaction(
                1,
                1,
                CurrencyType.EUR,
                BigDecimal.valueOf(20),
                CurrencyType.BRL,
                BigDecimal("5.598672"),
                createdAtMock
        )
        every { transactionRepository.save(any()) } returns transactionMock

        val exchangeRateMock = ExchangeRate(
                CurrencyType.EUR,
                LocalDate.now(),
                mapOf(CurrencyType.BRL to BigDecimal("5.598672")),
                true,
                Timestamp(System.currentTimeMillis())
        )
        every { exchangeRateService.getExchangeRatesDataLatest(transactionMock.sourceCurrency, transactionMock.destinationCurrency) } returns exchangeRateMock

        //when
        val result = transactionService.save(transactionMock)

        //then
        verify(exactly = 1) { transactionRepository.save(any()) }
        verify(exactly = 1) { exchangeRateService.getExchangeRatesDataLatest(transactionMock.sourceCurrency, transactionMock.destinationCurrency) }
        assertEquals(transactionMock, result)
    }

    @Test
    fun should_return_exchangeRateNotFoundException_when_exchange_rate_not_found() {
        //given
        val createdAtMock = LocalDateTime.now()
        val transactionMock = Transaction(
                1,
                1,
                CurrencyType.EUR,
                BigDecimal.valueOf(20),
                CurrencyType.BRL,
                BigDecimal("5.598672"),
                createdAtMock
        )
        every { transactionRepository.save(any()) } returns transactionMock

        val exchangeRateMock = ExchangeRate(
                CurrencyType.EUR,
                LocalDate.now(),
                mapOf(CurrencyType.USD to BigDecimal("5.598672")),
                true,
                Timestamp(System.currentTimeMillis())
        )
        every { exchangeRateService.getExchangeRatesDataLatest(transactionMock.sourceCurrency, transactionMock.destinationCurrency) } returns exchangeRateMock

        //then
        val exception = assertThrows(ExchangeRateNotFoundException::class.java) {
            transactionService.save(transactionMock)
        }
        assertEquals("Last exchange rate not found: BRL", exception.message)
    }

    @Test
    fun getByUserId() {
        //given
        val transactionsMock = listOf(
                Transaction(
                        1,
                        1,
                        CurrencyType.EUR,
                        BigDecimal.valueOf(10),
                        CurrencyType.BRL,
                        BigDecimal.valueOf(56),
                        LocalDateTime.now()
                )
        )
        every { transactionRepository.getByUserId(1) } returns transactionsMock

        //when
        val result = transactionService.getByUserId(1)

        //then
        verify(exactly = 1) { transactionRepository.getByUserId(1) }
        assertEquals(transactionsMock, result)
    }
}