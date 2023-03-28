package br.com.jaya.application.transaction

import br.com.jaya.domain.exception.ExchangeRateNotFoundException
import br.com.jaya.domain.exchangerate.ExchangeRateService
import br.com.jaya.domain.transaction.Transaction
import br.com.jaya.domain.transaction.TransactionRepository
import br.com.jaya.domain.transaction.TransactionService
import org.springframework.stereotype.Service

@Service
class TransactionServiceImpl(
        private val transactionRepository: TransactionRepository,
        private val exchangeRateService: ExchangeRateService
) : TransactionService {

    override fun save(transaction: Transaction): Transaction {

        val exchangeRatesDataLatest = exchangeRateService.getExchangeRatesDataLatest(
                transaction.sourceCurrency,
                transaction.destinationCurrency
        )
        val rate = exchangeRatesDataLatest.rates[transaction.destinationCurrency]
                ?: throw ExchangeRateNotFoundException("Last exchange rate not found: ${transaction.destinationCurrency}")
        transaction.conversionRate = rate
        return transactionRepository.save(transaction.toEntity())
    }

    override fun getByUserId(userId: Long): List<Transaction> {
        return transactionRepository.getByUserId(userId);
    }

}