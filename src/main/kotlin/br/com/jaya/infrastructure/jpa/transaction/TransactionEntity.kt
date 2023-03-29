package br.com.jaya.infrastructure.jpa.transaction

import br.com.jaya.domain.transaction.CurrencyType
import br.com.jaya.domain.transaction.Transaction
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "transactions")
class TransactionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    @Column(name = "user_id")
    val userId: Long,
    @Column(name = "source_currency")
    val sourceCurrency: CurrencyType,
    @Column(name = "source_value")
    val sourceValue: BigDecimal,
    @Column(name = "destination_currency")
    val destinationCurrency: CurrencyType,
    @Column(name = "conversion_rate")
    val conversionRate: BigDecimal?,
    @Column(name = "created_at")
    val createdAt: LocalDateTime,
) {
    fun toDomain(): Transaction {
        return Transaction(id, userId, sourceCurrency, sourceValue, destinationCurrency, conversionRate, createdAt)
    }
}
