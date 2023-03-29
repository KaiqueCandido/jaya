package br.com.jaya.domain.exception

import java.lang.RuntimeException

class ExchangeRateNotFoundException(message: String) : RuntimeException(message)
