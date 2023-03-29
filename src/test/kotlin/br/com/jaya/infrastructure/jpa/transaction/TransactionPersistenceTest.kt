package br.com.jaya.infrastructure.jpa.transaction

import br.com.jaya.domain.transaction.CurrencyType
import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.TestPropertySource
import java.math.BigDecimal
import java.time.LocalDateTime

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureEmbeddedDatabase
@TestPropertySource(
    properties = [
        "SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/mydb?createDatabaseIfNotExist=true",
        "SPRING_DATASOURCE_USERNAME=postgres",
        "SPRING_DATASOURCE_PASSWORD=postgres",
    ],
)
class TransactionPersistenceTest {

    @Autowired
    lateinit var entityManager: TestEntityManager

    @Autowired
    lateinit var transactionPersistence: TransactionPersistence

    @Test
    fun should_return_transaction_when_integration_with_database_is_success_when_find_by_user_id() {
        val transactionEntity = TransactionEntity(
            null,
            1,
            CurrencyType.EUR,
            BigDecimal.valueOf(20),
            CurrencyType.BRL,
            BigDecimal("5.598672"),
            LocalDateTime.now(),
        )
        entityManager.persist(transactionEntity)
        entityManager.flush()
        val transactions = transactionPersistence.findByUserId(1)
        assertEquals(transactions.size, 1)
    }
}
