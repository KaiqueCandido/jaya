package br.com.jaya.domain.exchangerate

import br.com.jaya.domain.transaction.CurrencyType

interface ExchangeRateService {

    fun getExchangeRatesDataLatest(base: CurrencyType, symbol: CurrencyType): ExchangeRate

}