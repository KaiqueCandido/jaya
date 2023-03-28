package br.com.jaya.infrastructure.feign.exchangerate

import br.com.jaya.domain.exception.ExchangeRateIntegrationException
import br.com.jaya.domain.exchangerate.ExchangeRate
import br.com.jaya.domain.exchangerate.ExchangeRateRepository
import br.com.jaya.domain.transaction.CurrencyType
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import java.lang.Exception

@Repository
class ExchangeRateRepositoryImpl(
        private val exchangeRateClient: ExchangeRateClient
) : ExchangeRateRepository {

    @Value("\${feign.client.exchange-rate.api-key}")
    lateinit var apikey: String

    override fun getExchangeRatesDataLatest(base: CurrencyType, symbol: CurrencyType): ExchangeRate {
        try {
            return exchangeRateClient.getExchangeRatesData(apikey, base, symbol).toDomain()
        } catch (e: Exception) {
            throw ExchangeRateIntegrationException("Integration error with exchangerates_data API")
        }
    }

}