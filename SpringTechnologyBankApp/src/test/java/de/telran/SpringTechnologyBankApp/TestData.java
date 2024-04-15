package de.telran.SpringTechnologyBankApp;

import de.telran.SpringTechnologyBankApp.dtos.bank.account.AccountDto;
import de.telran.SpringTechnologyBankApp.dtos.bank.account.AgreementForAccountDto;
import de.telran.SpringTechnologyBankApp.dtos.bank.account.TransactionForAccountDto;
import de.telran.SpringTechnologyBankApp.dtos.bank.agreement.AgreementDto;
import de.telran.SpringTechnologyBankApp.dtos.bank.client.ClientDto;
import de.telran.SpringTechnologyBankApp.dtos.bank.manager.ManagerDto;
import de.telran.SpringTechnologyBankApp.dtos.bank.manager.ManagerDtoForByCondition;
import de.telran.SpringTechnologyBankApp.dtos.bank.product.ProductDto;
import de.telran.SpringTechnologyBankApp.dtos.bank.transaction.TransactionDto;
import de.telran.SpringTechnologyBankApp.dtos.bank.transaction.TransactionResponseDto;
import de.telran.SpringTechnologyBankApp.entities.bank.*;
import de.telran.SpringTechnologyBankApp.entities.enums.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TestData {
    public final static LocalDateTime NOW = LocalDateTime.now();
    public final static Manager MANAGER = new Manager(1L, "TestManager", "Test", "testManager", "validPassword",
            "manager@test.com", "test description", StatusType.ACTIVE, RoleType.ROLE_MANAGER, null, NOW, null, null);
    public final static Manager MANAGER_NEW = new Manager(1L, "TestManager_new", "Test_updated", "testManager", "validPassword",
            "manager@test.com", "test description", StatusType.ACTIVE, RoleType.ROLE_MANAGER, null, NOW, null, null);

    public final static ManagerDto MANAGER_DTO = new ManagerDto(1L, "TestManager", "Test", "testManager", "validPassword",
            "manager@test.com", "test description", StatusType.ACTIVE, RoleType.ROLE_MANAGER, null, NOW, null, null);
    public final static ManagerDto MANAGER_DTO_NEW = new ManagerDto(1L, "TestManager_new", "Test_updated", "testManager", "validPassword",
            "manager@test.com", "test description", StatusType.ACTIVE, RoleType.ROLE_MANAGER, null, NOW, null, null);

    public static final Client CLIENT = new Client(1L, "123-453", "Client1", "LastName1", "Client1Login",
            "validPassword", "Client1@test.com", "Address1", "+41098464645", StatusType.ACTIVE,
            RoleType.ROLE_CLIENT, null, NOW, MANAGER, null);
    public static final Client CLIENT_2 = new Client(2L, "123-483", "Client2", "LastName2", "Client2Login",
            "validPassword", "Client2@test.com", "Address2", "+41098767545", StatusType.ACTIVE,
            RoleType.ROLE_CLIENT, null, NOW, MANAGER, null);

    public static final ClientDto CLIENT_DTO = new ClientDto(1L, "123-453", "Client1", "LastName1", "Client1Login",
            "validPassword", "Client1@test.com", "Address1", "+41098464645", StatusType.ACTIVE,
            RoleType.ROLE_CLIENT, null, NOW, MANAGER.getId(), null);

    public static final BigDecimal BIG_DECIMAL = new BigDecimal("0.05");
    public static final BigDecimal BIG_DECIMAL_LIMIT = new BigDecimal("100000.00");
    public static final BigDecimal BIG_DECIMAL_SUM = new BigDecimal("1000.00");
    public static final BigDecimal BIG_DECIMAL_SENDER_BALANCE = new BigDecimal("0.00");
    public static final Product PRODUCT = new Product(1L, "Test Product", StatusType.ACTIVE, ProductType.DEBIT_ACCOUNT,
            CurrencyCode.USD, BIG_DECIMAL, BIG_DECIMAL_LIMIT, null, NOW, MANAGER, null);
    public static final Product PRODUCT_UPDATED = new Product(1L, "Test Product updated", StatusType.ACTIVE, ProductType.DEBIT_ACCOUNT,
            CurrencyCode.USD, BIG_DECIMAL, BIG_DECIMAL_LIMIT, null, NOW, MANAGER, null);
    public static final ProductDto PRODUCT_DTO = new ProductDto(1L, "Test Product", StatusType.ACTIVE, ProductType.DEBIT_ACCOUNT,
            CurrencyCode.USD, BIG_DECIMAL, BIG_DECIMAL_LIMIT, null, NOW, MANAGER.getId(), null);
    public static final ProductDto PRODUCT_DTO_UPDATED = new ProductDto(1L, "Test Product updated", StatusType.ACTIVE, ProductType.DEBIT_ACCOUNT,
            CurrencyCode.USD, BIG_DECIMAL, BIG_DECIMAL_LIMIT, null, NOW, MANAGER.getId(), null);

    public static final Agreement AGREEMENT = new Agreement(1L, BIG_DECIMAL, BIG_DECIMAL_SUM, CurrencyCode.EUR,
            StatusType.ACTIVE, null, NOW, PRODUCT, null);

    public static final AgreementDto AGREEMENT_DTO = new AgreementDto(1L, BIG_DECIMAL, BIG_DECIMAL_SUM, CurrencyCode.EUR,
            StatusType.ACTIVE, null, NOW, PRODUCT.getId(), null);
    public static final AgreementForAccountDto AGREEMENT_FOR_ACCOUNT_DTO = new AgreementForAccountDto(1L,BIG_DECIMAL, BIG_DECIMAL_SUM, CurrencyCode.EUR,
            StatusType.ACTIVE);

    private static final Set<Agreement> AGREEMENTS = new HashSet<>(Collections.singletonList(AGREEMENT));
    private static final Set<AgreementForAccountDto> AGREEMENT_FOR_ACCOUNT_DTO_SET = new HashSet<>(Collections.singletonList(AGREEMENT_FOR_ACCOUNT_DTO));

    public static final TransactionForAccountDto TRANSACTION_FOR_ACCOUNT_DTO_DEBIT = new TransactionForAccountDto(1L, CurrencyCode.EUR, TransactionType.CASH);
    public static final TransactionForAccountDto TRANSACTION_FOR_ACCOUNT_DTO_CREDIT = new TransactionForAccountDto(1L, CurrencyCode.EUR, TransactionType.CASH);
    private static final Set<TransactionForAccountDto> TRANSACTION_FOR_ACCOUNT_DTO_DEBIT_SET = new HashSet<>(Collections.singletonList(TRANSACTION_FOR_ACCOUNT_DTO_DEBIT));
    private static final Set<TransactionForAccountDto> TRANSACTION_FOR_ACCOUNT_DTO_CREDIT_SET = new HashSet<>(Collections.singletonList(TRANSACTION_FOR_ACCOUNT_DTO_CREDIT));

    public static final Account ACCOUNT = new Account(1L, "TestAccount", BIG_DECIMAL_SUM, StatusType.ACTIVE, AccountType.DEBIT,
            CurrencyCode.EUR, NOW, NOW, CLIENT, AGREEMENTS,null,null);
    public static final Account ACCOUNT_RECIPIENT = new Account(2L, "TestAccountRecipient", BIG_DECIMAL_SUM, StatusType.ACTIVE, AccountType.CREDIT,
            CurrencyCode.EUR, NOW, NOW, CLIENT_2, AGREEMENTS, null, null);
    public static final Account ACCOUNT_UPDATING = new Account(1L, "TestAccountUpdating", BIG_DECIMAL_SUM, StatusType.ACTIVE, AccountType.DEBIT,
            CurrencyCode.EUR, NOW, NOW, CLIENT_2, AGREEMENTS,null,null);
    public static final AccountDto ACCOUNT_DTO = new AccountDto(1L, "TestAccount", BIG_DECIMAL_SUM, StatusType.ACTIVE, AccountType.DEBIT,
            CurrencyCode.EUR, NOW, NOW, CLIENT.getId(), AGREEMENT_FOR_ACCOUNT_DTO_SET, TRANSACTION_FOR_ACCOUNT_DTO_DEBIT_SET, TRANSACTION_FOR_ACCOUNT_DTO_CREDIT_SET);
    public static final AccountDto ACCOUNT_RECIPIENT_DTO = new AccountDto(2L, "TestAccountRecipient", BIG_DECIMAL_SUM, StatusType.ACTIVE, AccountType.DEBIT,
            CurrencyCode.EUR, NOW, NOW, CLIENT_2.getId(), AGREEMENT_FOR_ACCOUNT_DTO_SET, TRANSACTION_FOR_ACCOUNT_DTO_DEBIT_SET, TRANSACTION_FOR_ACCOUNT_DTO_CREDIT_SET);
    public static final AccountDto ACCOUNT_DTO_UPDATING = new AccountDto(1L, "TestAccountUpdating", BIG_DECIMAL_SUM, StatusType.ACTIVE, AccountType.DEBIT,
            CurrencyCode.EUR, NOW, NOW, CLIENT_2.getId(), AGREEMENT_FOR_ACCOUNT_DTO_SET, TRANSACTION_FOR_ACCOUNT_DTO_DEBIT_SET, TRANSACTION_FOR_ACCOUNT_DTO_CREDIT_SET);

    public static final Transaction TRANSACTION = new Transaction(1L, "key1", BIG_DECIMAL_SUM, "testTransaction",CurrencyCode.EUR, TransactionType.CASH, NOW, ACCOUNT, ACCOUNT_RECIPIENT);
    public static final TransactionDto TRANSACTION_DTO = new TransactionDto(1L, "key1", BIG_DECIMAL_SUM, "testTransaction",CurrencyCode.EUR, TransactionType.CASH, NOW, 1L, 2L);
    public static final TransactionResponseDto TRANSACTION_RESPONSE_DTO = new TransactionResponseDto(1L, "key1",BIG_DECIMAL_SUM, "testTransaction", CurrencyCode.EUR.toString(), TransactionType.CASH.toString(),  NOW, ACCOUNT.getId(), BIG_DECIMAL_SENDER_BALANCE, ACCOUNT_RECIPIENT.getId(), BIG_DECIMAL_SUM);
    public static final ManagerDtoForByCondition MANAGER_DTO_FOR_BY_CONDITION = new ManagerDtoForByCondition(1L, "TestManager", "Test",
            "manager@test.com", "test description", StatusType.ACTIVE);


}
