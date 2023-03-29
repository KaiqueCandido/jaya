package br.com.jaya.infrastructure.jpa.transaction

import br.com.jaya.domain.transaction.CurrencyType
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.math.BigDecimal
import java.time.LocalDateTime

class TransactionRepositoryImplTest {

    private val transactionPersistence: TransactionPersistence = mockk()
    private val transactionRepository = TransactionRepositoryImpl(transactionPersistence)

    @Test
    fun should_return_persisted_transaction_when_saved_with_success() {
        //given
        val transactionEntityInputMock = TransactionEntity(
                null,
                1,
                CurrencyType.EUR,
                BigDecimal.valueOf(20),
                CurrencyType.BRL,
                BigDecimal("5.598672"),
                LocalDateTime.now()
        )
        val transactionEntityOutputMock = TransactionEntity(
                1,
                1,
                CurrencyType.EUR,
                BigDecimal.valueOf(20),
                CurrencyType.BRL,
                BigDecimal("5.598672"),
                LocalDateTime.now()
        )
        every { transactionPersistence.save(transactionEntityInputMock) } returns transactionEntityOutputMock

        //when
        val result = transactionRepository.save(transactionEntityInputMock)

        //then
        verify(exactly = 1) { transactionPersistence.save(any()) }
        assertEquals(transactionEntityOutputMock.sourceCurrency, result.sourceCurrency)
        assertEquals(transactionEntityOutputMock.destinationCurrency, result.destinationCurrency)
        assertEquals(transactionEntityOutputMock.id, 1)
    }

    @Test
    fun should_return_transaction_persisted_with_success() {
        //given
        val transactionsEntityOutputMock = listOf(
                TransactionEntity(
                        1,
                        1,
                        CurrencyType.EUR,
                        BigDecimal.valueOf(20),
                        CurrencyType.BRL,
                        BigDecimal("5.598672"),
                        LocalDateTime.now()
                )
        )
        every { transactionPersistence.findByUserId(1) } returns transactionsEntityOutputMock

        //when
        val result = transactionRepository.getByUserId(1)

        //then
        verify(exactly = 1) { transactionPersistence.findByUserId(1) }
        assertEquals(result.size, 1)
    }
}