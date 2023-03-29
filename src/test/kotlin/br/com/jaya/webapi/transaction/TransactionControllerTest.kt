package br.com.jaya.webapi.transaction

import br.com.jaya.domain.transaction.CurrencyType
import br.com.jaya.domain.transaction.Transaction
import br.com.jaya.domain.transaction.TransactionService
import br.com.jaya.webapi.transaction.dto.TransactionDTO
import com.google.gson.Gson
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal
import java.time.LocalDateTime

@WebMvcTest
class TransactionControllerTest(
    @Autowired val mockMvc: MockMvc,
) {

    @MockkBean
    lateinit var transactionService: TransactionService

    @Test
    fun create_transaction_then_return_json_with_status_201() {
        val createdAtMock = LocalDateTime.now()
        val transactionOutputMock = Transaction(
            1,
            1,
            CurrencyType.EUR,
            BigDecimal.valueOf(20),
            CurrencyType.BRL,
            BigDecimal("5.598672"),
            createdAtMock,
        )
        every { transactionService.save(any()) } returns transactionOutputMock

        val transactionDTOMock = TransactionDTO(
            1,
            CurrencyType.EUR,
            BigDecimal.valueOf(20),
            CurrencyType.BRL,
        )
        mockMvc.perform(
            post("/transaction")
                .with(user("user").password("password"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(Gson().toJson(transactionDTOMock)),
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.sourceCurrency").value(transactionOutputMock.sourceCurrency.name))
            .andExpect(jsonPath("$.destinationCurrency").value(transactionOutputMock.destinationCurrency.name))
    }

    @Test
    fun get_transactions_filter_by_user_id_then_return_json_with_status_200() {
        val createdAtMock = LocalDateTime.now()
        val transactionsMock = listOf(
            Transaction(
                1,
                1,
                CurrencyType.EUR,
                BigDecimal.valueOf(20),
                CurrencyType.BRL,
                BigDecimal("5.598672"),
                createdAtMock,
            ),
        )
        every { transactionService.getByUserId(1) } returns transactionsMock

        mockMvc.perform(
            get("/transaction/user/1")
                .with(user("user").password("password"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpect(status().isAccepted)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun return_unauthorized_error_with_status_401() {
        val createdAtMock = LocalDateTime.now()
        val transactionsMock = listOf(
            Transaction(
                1,
                1,
                CurrencyType.EUR,
                BigDecimal.valueOf(20),
                CurrencyType.BRL,
                BigDecimal("5.598672"),
                createdAtMock,
            ),
        )
        every { transactionService.getByUserId(1) } returns transactionsMock

        mockMvc.perform(
            get("/transaction/user/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON),
        ).andExpect(status().isUnauthorized)
    }
}
