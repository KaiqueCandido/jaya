package br.com.jaya.webapi.transaction

import br.com.jaya.domain.transaction.TransactionService
import br.com.jaya.webapi.transaction.dto.TransactionDTO
import br.com.jaya.webapi.transaction.representation.TransactionRepresentation
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
class TransactionController(
        private val transactionService: TransactionService
) {

    @PostMapping
    fun createTransaction(@RequestBody transactionDto: TransactionDTO): ResponseEntity<TransactionRepresentation> {
        val transaction = transactionService.save(transactionDto.toDomain())
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction.toRepresentation())
    }

    @GetMapping("/user/{userId}")
    fun getTransactions(@PathVariable userId: Long): ResponseEntity<List<TransactionRepresentation>> {
        val transactions = transactionService.getByUserId(userId)
        val representations = transactions.map { transaction -> transaction.toRepresentation() }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(representations)
    }
}