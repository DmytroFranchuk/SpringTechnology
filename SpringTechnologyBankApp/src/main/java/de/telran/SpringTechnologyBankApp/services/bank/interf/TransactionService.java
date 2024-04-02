package de.telran.SpringTechnologyBankApp.services.bank.interf;

import de.telran.SpringTechnologyBankApp.dtos.bank.TransactionDto;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    TransactionDto createTransaction(TransactionDto transaction);

    List<TransactionDto> getAllTransactionsByClientId(Long clientId);

    List<TransactionDto> getAllTransactionsByClientIdForLastMonth(Long clientId);

    List<TransactionDto> getAllTransactionsWithAmountGreaterThan(BigDecimal amount);
}
