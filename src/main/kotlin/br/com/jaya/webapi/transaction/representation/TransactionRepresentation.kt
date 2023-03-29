package br.com.jaya.webapi.transaction.representation

import br.com.jaya.domain.transaction.CurrencyType
import java.math.BigDecimal
import java.time.LocalDateTime

class TransactionRepresentation(
    val transactionId: Long?,
    val userId: Long,
    val sourceCurrency: CurrencyType,
    val sourceValue: BigDecimal,
    val destinationCurrency: CurrencyType,
    val destinationValue: BigDecimal,
    val conversionRate: BigDecimal?,
    val createdAt: LocalDateTime,
)
