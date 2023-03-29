package br.com.jaya.webapi.transaction

import br.com.jaya.domain.transaction.TransactionService
import br.com.jaya.webapi.transaction.dto.TransactionDTO
import br.com.jaya.webapi.transaction.representation.TransactionRepresentation
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import io.swagger.v3.oas.annotations.parameters.RequestBody as OASRequestBody

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
class TransactionController(
    private val transactionService: TransactionService,
) {

    @Operation(summary = "Process transaction and save in database", description = "Returns 201 if successful")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Successful Operation"),
            ApiResponse(responseCode = "500", description = "Error in processing data transaction"),
        ],
    )
    @PostMapping
    fun createTransaction(
        @OASRequestBody(
            description = "Transaction for process",
            content = [
                Content(
                    mediaType = "application/json",
                ),
            ],
        )
        @RequestBody
        transactionDto: TransactionDTO,
    ): ResponseEntity<TransactionRepresentation> {
        val transaction = transactionService.save(transactionDto.toDomain())
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction.toRepresentation())
    }

    @Operation(summary = "List all of transactions filtered by user id", description = "Returns 202 if successful")
    @GetMapping("/user/{userId}")
    fun getTransactions(@PathVariable userId: Long): ResponseEntity<List<TransactionRepresentation>> {
        val transactions = transactionService.getByUserId(userId)
        val representations = transactions.map { transaction -> transaction.toRepresentation() }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(representations)
    }
}
