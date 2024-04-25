package de.telran.SpringTechnologyBankApp.services.bank.impl;

import de.telran.SpringTechnologyBankApp.dtos.bank.transaction.TransactionDto;
import de.telran.SpringTechnologyBankApp.dtos.bank.transaction.TransactionResponseDto;
import de.telran.SpringTechnologyBankApp.entities.bank.Account;
import de.telran.SpringTechnologyBankApp.entities.bank.Client;
import de.telran.SpringTechnologyBankApp.entities.bank.Transaction;
import de.telran.SpringTechnologyBankApp.entities.enums.AccountType;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import de.telran.SpringTechnologyBankApp.exceptions.NotActiveEntityException;
import de.telran.SpringTechnologyBankApp.exceptions.NotFoundEntityException;
import de.telran.SpringTechnologyBankApp.exceptions.NotValidTransactionException;
import de.telran.SpringTechnologyBankApp.mappers.bank.TransactionMapper;
import de.telran.SpringTechnologyBankApp.repositories.bank.AccountRepository;
import de.telran.SpringTechnologyBankApp.repositories.bank.ClientRepository;
import de.telran.SpringTechnologyBankApp.repositories.bank.TransactionRepository;
import de.telran.SpringTechnologyBankApp.services.bank.interf.TransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@SecurityRequirement(name = "BasicAuth")
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;
    private final TransactionMapper transactionMapper;

    /**
     * Создает новую транзакцию на основе информации из DTO транзакции.
     * Проверяет существование транзакции с заданным идентификатором идемпотентности.
     * Если транзакция с таким идентификатором уже существует, возвращает информацию о ней.
     * В противном случае создает новую транзакцию, сохраняет ее и обновляет балансы счетов отправителя и получателя.
     *
     * @param transactionDto DTO транзакции с информацией о транзакции
     * @return DTO ответа с информацией о созданной транзакции
     */
    @Override
    @Transactional
    public TransactionResponseDto createTransaction(TransactionDto transactionDto) {
        Long senderAccountId = transactionDto.getSenderAccountId();
        Long recipientAccountId = transactionDto.getRecipientAccountId();
        Account senderAccount = findActiveAccountById(senderAccountId);
        Account recipientAccount = findActiveAccountById(recipientAccountId);
        validateCurrencyAndAccountType(senderAccount, recipientAccount);
        BigDecimal amount = transactionDto.getAmount();
        validateSufficientFunds(senderAccount, amount);
        String idempotencyKey = transactionDto.getIdempotencyKey();
        Transaction existingTransaction = transactionRepository.findByIdempotencyKey(idempotencyKey);
        if (existingTransaction != null) {
            return transactionMapper.transactionToTransactionResponseDto(existingTransaction);
        }
        Transaction transaction = saveTransaction(transactionDto, senderAccount, recipientAccount, idempotencyKey);
        updateAccountBalances(senderAccount, recipientAccount, amount);

        return transactionMapper.transactionToTransactionResponseDto(transaction);
    }

    /**
     * Находит активный счет по его идентификатору.
     * Если счет не найден или неактивен, выбрасывает исключение.
     *
     * @param accountId идентификатор счета
     * @return активный счет
     * @throws NotActiveEntityException если счет не найден или неактивен
     */
    private Account findActiveAccountById(Long accountId) {
        Account account = accountRepository.findByIdAndStatusAccount(accountId, StatusType.ACTIVE);
        if (account == null) {
            throw new NotActiveEntityException("Счет не найден или неактивен идентификатор id: " + accountId);
        }
        return account;
    }

    /**
     * Проверяет соответствие валюты и типа счетов отправителя и получателя.
     * Если валюты не совпадают или типы счетов не соответствуют требованиям транзакции,
     * выбрасывает исключение.
     *
     * @param senderAccount     счет отправителя
     * @param recipientAccount  счет получателя
     * @throws NotValidTransactionException если валюты не совпадают или типы счетов не соответствуют требованиям транзакции
     */
    private void validateCurrencyAndAccountType(Account senderAccount, Account recipientAccount) {
        if (!senderAccount.getCurrencyCode().equals(recipientAccount.getCurrencyCode())) {
            throw new NotValidTransactionException("Несоответствие валюты между счетами отправителя и получателя");
        }
        if (!senderAccount.getAccountType().equals(AccountType.DEBIT) ||
                !recipientAccount.getAccountType().equals(AccountType.CREDIT)) {
            throw new NotValidTransactionException("Несоответствие типов между счетами отправителя и получателя");
        }
    }

    /**
     * Проверяет достаточность средств на счете отправителя для проведения транзакции.
     * Если сумма транзакции превышает баланс счета отправителя, выбрасывает исключение.
     *
     * @param senderAccount счет отправителя
     * @param amount        сумма транзакции
     * @throws NotValidTransactionException если сумма транзакции превышает баланс счета отправителя
     */
    private void validateSufficientFunds(Account senderAccount, BigDecimal amount) {
        if (senderAccount.getBalance().compareTo(amount) < 0) {
            throw new NotValidTransactionException("Недостаточно средств на счете отправителя");
        }
    }

    /**
     * Сохраняет новую транзакцию в базу данных.
     *
     * @param transactionDto     данные о транзакции
     * @param senderAccount      счет отправителя
     * @param recipientAccount   счет получателя
     * @param idempotencyKey     идентификатор транзакции для обеспечения идемпотентности
     * @return сохраненная транзакция
     */
    private Transaction saveTransaction(
            TransactionDto transactionDto,
            Account senderAccount,
            Account recipientAccount,
            String idempotencyKey) {
        Transaction transaction = new Transaction();
        transaction.setIdempotencyKey(idempotencyKey);
        transaction.setAmount(transactionDto.getAmount());
        transaction.setDescription(transactionDto.getDescription());
        transaction.setCurrencyCode(transactionDto.getCurrencyCode());
        transaction.setTransactionType(transactionDto.getTransactionType());
        transaction.setDebitAccount(senderAccount);
        transaction.setCreditAccount(recipientAccount);
        return transactionRepository.save(transaction);
    }

    /**
     * Обновляет балансы счетов после проведения транзакции.
     *
     * @param senderAccount      счет отправителя
     * @param recipientAccount   счет получателя
     * @param amount             сумма транзакции
     */
    private void updateAccountBalances(Account senderAccount, Account recipientAccount, BigDecimal amount) {
        senderAccount.setBalance(senderAccount.getBalance().subtract(amount));
        recipientAccount.setBalance(recipientAccount.getBalance().add(amount));
        accountRepository.saveAll(Arrays.asList(senderAccount, recipientAccount));
    }

    /**
     * Получает список всех транзакций для указанного идентификатора клиента.
     *
     * @param clientId идентификатор клиента
     * @return список транзакций для указанного клиента
     * @throws NotFoundEntityException если клиент с указанным идентификатором не найден
     */
    @Override
    public List<TransactionDto> getAllTransactionsByClientId(Long clientId) {
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        if (clientOptional.isEmpty()) {
            throw new NotFoundEntityException("Нет клиента с id: " + clientId);
        }
        List<Transaction> transactions = transactionRepository.findAllTransactionsByClientId(clientId);
        return transactions.stream()
                .map(transactionMapper::transactionToTransactionDto)
                .collect(Collectors.toList());
    }

    /**
     * Получает список всех транзакций для указанного идентификатора клиента за последний месяц.
     *
     * @param clientId идентификатор клиента
     * @return список транзакций для указанного клиента за последний месяц
     */
    @Override
    public List<TransactionDto> getAllTransactionsByClientIdForLastMonth(Long clientId) {
        LocalDate startDate = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        LocalDateTime startDateWithTime = startDate.atStartOfDay();
        List<Transaction> transactions = transactionRepository
                .findAllTransactionsByClientIdForLastMonth(clientId, startDateWithTime);
        return transactions.stream()
                .map(transactionMapper::transactionToTransactionDto)
                .collect(Collectors.toList());
    }

    /**
     * Получает список всех транзакций с суммой больше указанной.
     *
     * @param amount минимальная сумма транзакции
     * @return список транзакций с суммой больше указанной
     */
    @Override
    public List<TransactionDto> getAllTransactionsWithAmountGreaterThan(BigDecimal amount) {
        List<Transaction> transactions = transactionRepository.findAllTransactionsWithAmountGreaterThan(amount);
        return transactions.stream()
                .map(transactionMapper::transactionToTransactionDto)
                .collect(Collectors.toList());
    }
}
