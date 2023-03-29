package br.com.jaya.domain.transaction

import br.com.jaya.infrastructure.jpa.transaction.TransactionEntity
import br.com.jaya.webapi.transaction.representation.TransactionRepresentation
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime

class Transaction(
    private val id: Long?,
    private val userId: Long,
    val sourceCurrency: CurrencyType,
    private val sourceValue: BigDecimal,
    val destinationCurrency: CurrencyType,
    var conversionRate: BigDecimal?,
    private val createdAt: LocalDateTime,
) {

    fun toEntity(): TransactionEntity {
        return TransactionEntity(
            id,
            userId,
            sourceCurrency,
            sourceValue,
            destinationCurrency,
            conversionRate,
            createdAt,
        )
    }

    fun toRepresentation(): TransactionRepresentation {
        return TransactionRepresentation(
            id,
            userId,
            sourceCurrency,
            sourceValue,
            destinationCurrency,
            sourceValue.multiply(conversionRate).setScale(SCALE, RoundingMode.DOWN),
            conversionRate,
            createdAt,
        )
    }

    companion object {
        const val SCALE: Int = 2
    }
}
