package br.com.jaya.webapi.transaction.dto

import br.com.jaya.domain.transaction.CurrencyType
import br.com.jaya.domain.transaction.Transaction
import java.math.BigDecimal
import java.time.LocalDateTime

class TransactionDTO (
        private val userId: Long,
        private val sourceCurrency: CurrencyType,
        private val sourceValue: BigDecimal,
        private val destinationCurrency: CurrencyType
) {
    fun toDomain(): Transaction {
        return Transaction(
                null,
                userId,
                sourceCurrency,
                sourceValue,
                destinationCurrency,
                null,
                LocalDateTime.now()
        )
    }
}