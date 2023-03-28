package br.com.jaya.application.exchangerate

import br.com.jaya.domain.exchangerate.ExchangeRate
import br.com.jaya.domain.exchangerate.ExchangeRateRepository
import br.com.jaya.domain.exchangerate.ExchangeRateService
import br.com.jaya.domain.transaction.CurrencyType
import org.springframework.stereotype.Service

@Service
class ExchangeRateServiceImpl(
        private val exchangeRateRepository: ExchangeRateRepository
) : ExchangeRateService {

    override fun getExchangeRatesDataLatest(base: CurrencyType, symbol: CurrencyType): ExchangeRate {
        return exchangeRateRepository.getExchangeRatesDataLatest(base, symbol)
    }

}