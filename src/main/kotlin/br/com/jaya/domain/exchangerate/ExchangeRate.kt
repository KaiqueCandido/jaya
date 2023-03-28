package br.com.jaya.domain.exchangerate

import br.com.jaya.domain.transaction.CurrencyType
import java.math.BigDecimal
import java.sql.Timestamp
import java.time.LocalDate

class ExchangeRate (
    val base: CurrencyType,
    val date: LocalDate,
    val rates: Map<CurrencyType, BigDecimal>,
    val success: Boolean,
    val timestamp: Timestamp
)