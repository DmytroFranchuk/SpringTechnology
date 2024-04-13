package de.telran.SpringTechnologyBankApp.services.bank.interf;

import de.telran.SpringTechnologyBankApp.dtos.bank.transaction.TransactionDto;
import de.telran.SpringTechnologyBankApp.dtos.bank.transaction.TransactionResponseDto;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    TransactionResponseDto createTransaction(TransactionDto transaction);

    List<TransactionDto> getAllTransactionsByClientId(Long clientId);

    List<TransactionDto> getAllTransactionsByClientIdForLastMonth(Long clientId);

    List<TransactionDto> getAllTransactionsWithAmountGreaterThan(BigDecimal amount);
}
