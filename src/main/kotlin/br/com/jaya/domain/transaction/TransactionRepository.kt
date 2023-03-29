package br.com.jaya.domain.transaction

import br.com.jaya.infrastructure.jpa.transaction.TransactionEntity

interface TransactionRepository {

    fun save(transactionEntity: TransactionEntity): Transaction

    fun getByUserId(userId: Long): List<Transaction>
}
