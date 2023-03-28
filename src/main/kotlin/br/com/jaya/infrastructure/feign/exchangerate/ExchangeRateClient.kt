package br.com.jaya.infrastructure.feign.exchangerate

import br.com.jaya.domain.transaction.CurrencyType
import br.com.jaya.infrastructure.feign.exchangerate.dto.ExchangeRateDTO
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
        name="\${feign.client.exchange-rate.name}",
        url ="\${feign.client.exchange-rate.url}"
)
interface ExchangeRateClient {

    @GetMapping("exchangerates_data/latest")
    fun getExchangeRatesData(
            @RequestHeader apikey: String,
            @RequestParam base: CurrencyType,
            @RequestParam symbols: CurrencyType
    ): ExchangeRateDTO
}