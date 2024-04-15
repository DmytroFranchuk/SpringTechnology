package de.telran.SpringTechnologyBankApp.entities.bank;

import de.telran.SpringTechnologyBankApp.entities.enums.CurrencyCode;
import de.telran.SpringTechnologyBankApp.entities.enums.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransactionTest {
    private Transaction transaction;

    @Mock
    private Account debitAccount;

    @Mock
    private Account creditAccount;

    @BeforeEach
    void setUp() {
        transaction = new Transaction();
        transaction.setId(1L);
        transaction.setIdempotencyKey("test-idempotency-key");
        transaction.setAmount(BigDecimal.TEN);
        transaction.setDescription("Test transaction");
        transaction.setCurrencyCode(CurrencyCode.USD);
        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setDebitAccount(debitAccount);
        transaction.setCreditAccount(creditAccount);
    }

    @Test
    void testConstructors() {
        Transaction emptyTransaction = new Transaction();
        assertNull(emptyTransaction.getId());
        assertNull(emptyTransaction.getIdempotencyKey());
        assertNull(emptyTransaction.getAmount());
        assertNull(emptyTransaction.getDescription());
        assertNull(emptyTransaction.getCurrencyCode());
        assertNull(emptyTransaction.getTransactionType());
        assertNull(emptyTransaction.getCreatedAt());
        assertNull(emptyTransaction.getDebitAccount());
        assertNull(emptyTransaction.getCreditAccount());

        Transaction parameterizedTransaction = new Transaction(2L, "param-idempotency-key", BigDecimal.ONE, "Parameterized transaction", CurrencyCode.EUR, TransactionType.PAYMENT, LocalDateTime.now().plusDays(1), null, null);
        assertEquals(2L, parameterizedTransaction.getId());
        assertEquals("param-idempotency-key", parameterizedTransaction.getIdempotencyKey());
        assertEquals(BigDecimal.ONE, parameterizedTransaction.getAmount());
        assertEquals("Parameterized transaction", parameterizedTransaction.getDescription());
        assertEquals(CurrencyCode.EUR, parameterizedTransaction.getCurrencyCode());
        assertEquals(TransactionType.PAYMENT, parameterizedTransaction.getTransactionType());
        assertEquals(LocalDateTime.now().plusDays(1).getDayOfYear(), parameterizedTransaction.getCreatedAt().getDayOfYear());
        assertNull(parameterizedTransaction.getDebitAccount());
        assertNull(parameterizedTransaction.getCreditAccount());
    }

    @Test
    void testEqualsAndHashCode() {
        Transaction sameTransaction = new Transaction(1L, "test-idempotency-key", BigDecimal.TEN, "Test transaction", CurrencyCode.USD, TransactionType.TRANSFER, LocalDateTime.now(), debitAccount, creditAccount);
        Transaction differentTransaction = new Transaction(2L, "different-idempotency-key", BigDecimal.ONE, "Different transaction", CurrencyCode.EUR, TransactionType.PAYMENT, LocalDateTime.now().plusDays(1), null, null);

        assertEquals(transaction, sameTransaction);
        assertNotEquals(transaction, differentTransaction);
        assertEquals(transaction.hashCode(), sameTransaction.hashCode());
        assertNotEquals(transaction.hashCode(), differentTransaction.hashCode());
    }

    @Test
    void testGetCreatedAtAndSetCreatedAt() {
        LocalDateTime newDateTime = LocalDateTime.now().minusDays(1);
        transaction.setCreatedAt(newDateTime);
        assertEquals(newDateTime, transaction.getCreatedAt());
    }

    @Test
    void testTransactionToString() {
        String expected = "\nTransaction{id=1, idempotencyKey='test-idempotency-key', amount=10, description='Test transaction', currencyCode=USD, transactionType=TRANSFER, createdAt=" + transaction.getCreatedAt() + ", debitAccount=" + debitAccount + ", creditAccount=" + creditAccount + '}';
        assertEquals(expected, transaction.toString());
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(1L, transaction.getId());
        assertEquals("test-idempotency-key", transaction.getIdempotencyKey());
        assertEquals(BigDecimal.TEN, transaction.getAmount());
        assertEquals("Test transaction", transaction.getDescription());
        assertEquals(CurrencyCode.USD, transaction.getCurrencyCode());
        assertEquals(TransactionType.TRANSFER, transaction.getTransactionType());
        assertEquals(LocalDateTime.now().getDayOfYear(), transaction.getCreatedAt().getDayOfYear());
        assertEquals(debitAccount, transaction.getDebitAccount());
        assertEquals(creditAccount, transaction.getCreditAccount());

        transaction.setId(2L);
        transaction.setIdempotencyKey("new-idempotency-key");
        transaction.setAmount(BigDecimal.ONE);
        transaction.setDescription("New transaction");
        transaction.setCurrencyCode(CurrencyCode.EUR);
        transaction.setTransactionType(TransactionType.PAYMENT);
        transaction.setCreatedAt(LocalDateTime.now().plusDays(1));
        transaction.setDebitAccount(null);
        transaction.setCreditAccount(null);

        assertEquals(2L, transaction.getId());
        assertEquals("new-idempotency-key", transaction.getIdempotencyKey());
        assertEquals(BigDecimal.ONE, transaction.getAmount());
        assertEquals("New transaction", transaction.getDescription());
        assertEquals(CurrencyCode.EUR, transaction.getCurrencyCode());
        assertEquals(TransactionType.PAYMENT, transaction.getTransactionType());
        assertEquals(LocalDateTime.now().plusDays(1).getDayOfYear(), transaction.getCreatedAt().getDayOfYear());
        assertNull(transaction.getDebitAccount());
        assertNull(transaction.getCreditAccount());
    }
}