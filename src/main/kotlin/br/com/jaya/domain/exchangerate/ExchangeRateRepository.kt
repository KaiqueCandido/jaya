package br.com.jaya.domain.exchangerate

import br.com.jaya.domain.transaction.CurrencyType
import org.springframework.web.bind.annotation.RequestParam

interface ExchangeRateRepository {

    fun getExchangeRatesDataLatest(base: CurrencyType, symbol: CurrencyType): ExchangeRate

}