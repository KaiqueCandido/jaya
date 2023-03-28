package br.com.jaya.infrastructure.feign.exchangerate.dto

import br.com.jaya.domain.exchangerate.ExchangeRate
import br.com.jaya.domain.transaction.CurrencyType
import java.math.BigDecimal
import java.sql.Timestamp
import java.time.LocalDate

class ExchangeRateDTO(
        private val base: CurrencyType,
        private val date: LocalDate,
        private val rates: Map<CurrencyType, BigDecimal>,
        private val success: Boolean,
        private val timestamp: Timestamp
) {
    fun toDomain(): ExchangeRate {
        return ExchangeRate(base, date, rates, success, timestamp)
    }
}