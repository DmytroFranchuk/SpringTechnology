package de.telran.SpringTechnologyBankApp.controllers.bank;

import de.telran.SpringTechnologyBankApp.dtos.bank.transaction.TransactionDto;
import de.telran.SpringTechnologyBankApp.dtos.bank.transaction.TransactionResponseDto;
import de.telran.SpringTechnologyBankApp.services.bank.interf.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
@Tag(name = "Transaction Controller API")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/create")
    @Operation(summary = "Create a new transaction")
    public ResponseEntity<TransactionResponseDto> createTransaction(
            @Valid
            @RequestBody TransactionDto transactionDto) {

        TransactionResponseDto createdTransaction = transactionService.createTransaction(transactionDto);
        return ResponseEntity.ok(createdTransaction);
    }
}
