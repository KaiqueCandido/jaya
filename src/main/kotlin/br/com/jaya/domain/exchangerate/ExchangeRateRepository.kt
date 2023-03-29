package br.com.jaya.domain.exchangerate

import br.com.jaya.domain.transaction.CurrencyType

interface ExchangeRateRepository {

    fun getExchangeRatesDataLatest(base: CurrencyType, symbol: CurrencyType): ExchangeRate
}
