package br.com.jaya.infrastructure.feign.exchangerate

import br.com.jaya.domain.exception.ExchangeRateIntegrationException
import br.com.jaya.domain.exception.ExchangeRateNotFoundException
import br.com.jaya.domain.transaction.CurrencyType
import br.com.jaya.infrastructure.feign.exchangerate.dto.ExchangeRateDTO
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.springframework.test.context.TestPropertySource
import java.math.BigDecimal
import java.sql.Timestamp
import java.time.LocalDate

class ExchangeRateRepositoryImplTest {

    private val exchangeRateClient: ExchangeRateClient = mockk()
    private val exchangeRateRepository = ExchangeRateRepositoryImpl(exchangeRateClient)

    @Test
    fun should_return_exchange_rate_with_success() {
        //given
        val exchangeRateDTOMock = ExchangeRateDTO(
                CurrencyType.EUR,
                LocalDate.now(),
                mapOf(CurrencyType.BRL to BigDecimal("5.598672")),
                true,
                Timestamp(System.currentTimeMillis())
        )
        exchangeRateRepository.apikey = "apikey"
        every { exchangeRateClient.getExchangeRatesData("apikey", CurrencyType.EUR, CurrencyType.BRL) } returns exchangeRateDTOMock

        //when
        val result = exchangeRateRepository.getExchangeRatesDataLatest(CurrencyType.EUR, CurrencyType.BRL)

        //then
        assertEquals(exchangeRateDTOMock.toDomain().toString(), result.toString())
    }

    @Test
    fun should_return_exchange_rate_integration_exceptionexception_when_integration_failed() {
        //given
        val exchangeRateDTOMock = ExchangeRateDTO(
                CurrencyType.EUR,
                LocalDate.now(),
                mapOf(CurrencyType.BRL to BigDecimal("5.598672")),
                true,
                Timestamp(System.currentTimeMillis())
        )
        exchangeRateRepository.apikey = "apikey"
        every { exchangeRateClient.getExchangeRatesData("apikey", CurrencyType.EUR, CurrencyType.BRL) }
                .throws(ExchangeRateIntegrationException("Integration error with exchangerates_data API"))

        //then
        val exception = assertThrows(ExchangeRateIntegrationException::class.java) {
            exchangeRateRepository.getExchangeRatesDataLatest(CurrencyType.EUR, CurrencyType.BRL)
        }
        assertEquals("Integration error with exchangerates_data API", exception.message)
    }
}