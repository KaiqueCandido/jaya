package br.com.jaya.domain.transaction

interface TransactionService {

    fun save(transaction: Transaction): Transaction

    fun getByUserId(userId: Long): List<Transaction>

}