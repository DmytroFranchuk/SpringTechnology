package de.telran.SpringTechnologyBankApp.repositories.bank;

import de.telran.SpringTechnologyBankApp.entities.bank.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findByIdempotencyKey(String idempotencyKey);

    @Query("SELECT t FROM Transaction t WHERE t.debitAccount.client.id " +
            "= :clientId OR t.creditAccount.client.id = :clientId")
    List<Transaction> findAllTransactionsByClientId(Long clientId);

    @Query("SELECT t FROM Transaction t WHERE (t.debitAccount.client.id = :clientId " +
            "OR t.creditAccount.client.id = :clientId) AND t.createdAt >= :startDate")
    List<Transaction> findAllTransactionsByClientIdForLastMonth(Long clientId, LocalDateTime startDate);

    @Query("SELECT t FROM Transaction t WHERE t.amount > :amount")
    List<Transaction> findAllTransactionsWithAmountGreaterThan(BigDecimal amount);
}
