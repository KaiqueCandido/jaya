package br.com.jaya.infrastructure.jpa.transaction

import org.springframework.data.jpa.repository.JpaRepository

interface TransactionPersistence : JpaRepository<TransactionEntity, Long> {

    fun findByUserId(userId: Long): List<TransactionEntity>

}
