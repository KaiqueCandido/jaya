package br.com.jaya.application.exchangerate

import br.com.jaya.domain.exchangerate.ExchangeRate
import br.com.jaya.domain.exchangerate.ExchangeRateRepository
import br.com.jaya.domain.transaction.CurrencyType
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.sql.Timestamp
import java.time.LocalDate

class ExchangeRateServiceImplTest {

    private val exchangeRateRepository: ExchangeRateRepository = mockk()
    private val exchangeRateService = ExchangeRateServiceImpl(exchangeRateRepository)

    @Test
    fun should_return_exchange_rates_latest_with_success() {
        // given
        val exchangeRateMock = ExchangeRate(
            CurrencyType.EUR,
            LocalDate.now(),
            mapOf(CurrencyType.BRL to BigDecimal("5.598672")),
            true,
            Timestamp(System.currentTimeMillis()),
        )
        every {
            exchangeRateRepository.getExchangeRatesDataLatest(CurrencyType.EUR, CurrencyType.USD)
        } returns exchangeRateMock

        // when
        val result = exchangeRateService.getExchangeRatesDataLatest(CurrencyType.EUR, CurrencyType.USD)

        // then
        verify(exactly = 1) { exchangeRateRepository.getExchangeRatesDataLatest(CurrencyType.EUR, CurrencyType.USD) }
        Assertions.assertEquals(exchangeRateMock, result)
    }
}
