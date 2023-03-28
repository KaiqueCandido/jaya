package br.com.jaya.infrastructure.jpa.transaction

import br.com.jaya.domain.transaction.Transaction
import br.com.jaya.domain.transaction.TransactionRepository
import org.springframework.stereotype.Repository

@Repository
class TransactionRepositoryImpl(
        private val transactionPersistence: TransactionPersistence
) : TransactionRepository {

    override fun save(transactionEntity: TransactionEntity): Transaction {
        return transactionPersistence.save(transactionEntity).toDomain();
    }

    override fun getByUserId(userId: Long): List<Transaction> {
        val lstTransactionEntity = transactionPersistence.findByUserId(userId)
        return lstTransactionEntity.map { transactionEntity -> transactionEntity.toDomain() }
    }

}