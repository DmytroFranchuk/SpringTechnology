package de.telran.SpringTechnologyBankApp.services.bank.impl;

import de.telran.SpringTechnologyBankApp.dtos.bank.transaction.TransactionDto;
import de.telran.SpringTechnologyBankApp.services.bank.interf.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    @Override
    public TransactionDto createTransaction(TransactionDto transaction) {
        return null;
    }

    @Override
    public List<TransactionDto> getAllTransactionsByClientId(Long clientId) {
        return null;
    }

    @Override
    public List<TransactionDto> getAllTransactionsByClientIdForLastMonth(Long clientId) {
        return null;
    }

    @Override
    public List<TransactionDto> getAllTransactionsWithAmountGreaterThan(BigDecimal amount) {
        return null;
    }
}
