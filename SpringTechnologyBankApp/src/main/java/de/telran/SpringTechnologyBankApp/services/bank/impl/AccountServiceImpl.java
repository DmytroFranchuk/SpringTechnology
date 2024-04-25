package de.telran.SpringTechnologyBankApp.services.bank.impl;

import de.telran.SpringTechnologyBankApp.dtos.bank.account.AccountDto;
import de.telran.SpringTechnologyBankApp.dtos.bank.account.AgreementForAccountDto;
import de.telran.SpringTechnologyBankApp.entities.bank.Account;
import de.telran.SpringTechnologyBankApp.entities.bank.Agreement;
import de.telran.SpringTechnologyBankApp.entities.bank.Client;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import de.telran.SpringTechnologyBankApp.exceptions.NotExistEntityException;
import de.telran.SpringTechnologyBankApp.exceptions.NotFoundEntityException;
import de.telran.SpringTechnologyBankApp.exceptions.NotUpdatedEntityException;
import de.telran.SpringTechnologyBankApp.mappers.bank.AccountMapper;
import de.telran.SpringTechnologyBankApp.repositories.bank.AccountRepository;
import de.telran.SpringTechnologyBankApp.repositories.bank.AgreementRepository;
import de.telran.SpringTechnologyBankApp.repositories.bank.ClientRepository;
import de.telran.SpringTechnologyBankApp.services.bank.interf.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static de.telran.SpringTechnologyBankApp.services.utilities.Utils.updateFieldIfNotNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private static final String MISSING_AGREEMENTS_IDS_ERROR = "Не удалось создать счет, договоры с указанными id не найдены ";
    private static final String MISSING_AGREEMENTS_ERROR = "Не удалось создать счет, не указан номер договора ";
    private static final String CLIENT_NOT_FOUND_ERROR = "Клиент с указанным id не найден ";
    public final AgreementRepository agreementRepository;
    public final AccountRepository accountRepository;
    public final ClientRepository clientRepository;
    public final AccountMapper accountMapper;

    /**
     * Создает новый счет на основе переданных данных в объекте {@link AccountDto}.
     * При создании счета проверяет существование клиента по идентификатору, указанному в {@code accountDto}.
     * Проверяет наличие договоров, связанных со счетом, и их соответствие существующим договорам в репозитории.
     * Если все проверки пройдены успешно, создает новый счет на основе данных из {@code accountDto},
     * сохраняет его в репозитории и возвращает объект {@link AccountDto}, соответствующий созданному счету.
     *
     * @param accountDto объект {@link AccountDto}, содержащий данные для создания нового счета
     * @return объект {@link AccountDto}, представляющий созданный счет
     * @throws NotExistEntityException        если клиент с указанным идентификатором не найден
     * @throws NotExistEntityException        если в объекте {@code accountDto} не указаны договоры
     * @throws IllegalArgumentException       если один или несколько идентификаторов договоров в {@code accountDto}
     *                                        не соответствуют ни одному договору в репозитории
     * @throws DataIntegrityViolationException если нарушена целостность данных при сохранении счета
     * @throws RuntimeException               если возникает другое исключение при создании счета
     */
    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        try {
            Client client = clientRepository.findById(accountDto.getClientId())
                    .orElseThrow(() -> new NotExistEntityException(CLIENT_NOT_FOUND_ERROR
                            + accountDto.getClientId()));
            Set<AgreementForAccountDto> agreements = accountDto.getAgreements();
            if (agreements == null || agreements.isEmpty()) {
                throw new NotExistEntityException(MISSING_AGREEMENTS_ERROR);
            }
            Set<Long> agreementIds = agreements.stream()
                    .map(AgreementForAccountDto::getId)
                    .collect(Collectors.toSet());
            Set<Long> missingAgreementIds = new HashSet<>();

            for (Long agreementId : agreementIds) {
                if (!agreementRepository.existsById(agreementId)) {
                    missingAgreementIds.add(agreementId);
                }
            }
            if (!missingAgreementIds.isEmpty()) {
                throw new IllegalArgumentException(MISSING_AGREEMENTS_IDS_ERROR + missingAgreementIds);
            }
            Account account = accountMapper.accountDtoToAccount(accountDto);
            Account savedAccount = accountRepository.save(account);
            return accountMapper.accountToAccountDto(savedAccount);
        } catch (DataIntegrityViolationException exception) {
            String errorMessage = exception.getMessage();
            throw new DataIntegrityViolationException(errorMessage);
        } catch (Exception exception) {
            log.error("Не удалось создать счет", exception);
            throw new RuntimeException("Не удалось создать счет", exception);
        }
    }

    /**
     * Получает счет по указанному идентификатору.
     * Если счет с указанным идентификатором найден, возвращает объект {@link AccountDto},
     * содержащий информацию о найденном счете.
     *
     * @param id идентификатор счета
     * @return объект {@link AccountDto}, представляющий найденный счет
     * @throws NotFoundEntityException если счет с указанным идентификатором не найден
     */
    @Override
    public AccountDto getAccountById(Long id) {
        return accountRepository.findById(id).map(accountMapper::accountToAccountDto)
                .orElseThrow(() -> new NotFoundEntityException("Не найден счет с id: " + id));
    }

    /**
     * Обновляет информацию о счете по указанному идентификатору.
     * Если счет с указанным идентификатором найден, обновляет его поля на основе переданных данных.
     * Если поле {@code clientId} в переданном объекте {@code account} не равно {@code null},
     * производит поиск клиента с указанным идентификатором. Если клиент найден, устанавливает его в качестве клиента для счета.
     *
     * @param id      идентификатор счета, который нужно обновить
     * @param account объект {@link AccountDto}, содержащий новую информацию о счете
     * @return объект {@link AccountDto}, представляющий обновленный счет
     * @throws NotFoundEntityException   если счет с указанным идентификатором не найден
     * @throws NotExistEntityException   если клиент с указанным идентификатором не найден
     * @throws NotUpdatedEntityException если не удалось обновить счет с указанным идентификатором
     */
    @Override
    public AccountDto updateAccountById(Long id, AccountDto account) {
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("Счет с id: " + id + " не найден"));
        Long clientId = account.getClientId();
        if (clientId != null) {
            Client client = clientRepository.findById(clientId)
                    .orElseThrow(() -> new NotExistEntityException("Клиент с id: " + clientId + " не найден"));
            existingAccount.setClient(client);
        }
        updateAccountFields(account, existingAccount);
        updateAgreements(account, existingAccount);
        try {
            Account updatedAccount = accountRepository.save(existingAccount);
            return accountMapper.accountToAccountDto(updatedAccount);
        } catch (Exception exception) {
            throw new NotUpdatedEntityException("Не удалось обновить счет с id: " + id);
        }
    }

    /**
     * Удаляет счет с указанным идентификатором.
     * Если счет с указанным идентификатором не найден, генерируется исключение {@link NotFoundEntityException}.
     * После удаления счета его статус устанавливается на "REMOVED".
     *
     * @param id идентификатор счета, который нужно удалить
     * @throws NotFoundEntityException если счет с указанным идентификатором не найден
     */
    @Override
    public void deleteAccountById(Long id) {
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("Клиент с id: " + id + " не найден"));
        existingAccount.setStatusAccount(StatusType.REMOVED);
        accountRepository.save(existingAccount);
    }

    /**
     * Возвращает список всех счетов с указанным статусом.
     *
     * @param status статус счетов, по которому нужно выполнить поиск
     * @return список счетов с указанным статусом в виде списка объектов {@link AccountDto}
     */
    @Override
    public List<AccountDto> getAllAccountsWhereStatusTypeIs(StatusType status) {
        List<Account> accounts = accountRepository.findAccountsByStatusAccount(status);
        return accountMapper.accountsToAccountDtos(accounts);
    }

    /**
     * Возвращает список счетов, принадлежащих указанному клиенту.
     *
     * @param clientId идентификатор клиента, чьи счета необходимо найти
     * @return список счетов, принадлежащих указанному клиенту, в виде списка объектов {@link AccountDto}
     */
    @Override
    public List<AccountDto> getAccountsByClientId(Long clientId) {
        List<Account> accounts = accountRepository.findAccountsByClientId(clientId);
        return accountMapper.accountsToAccountDtos(accounts);
    }

    /**
     * Возвращает список счетов, управляемых указанным менеджером.
     *
     * @param managerId идентификатор менеджера, чьи счета необходимо найти
     * @return список счетов, управляемых указанным менеджером, в виде списка объектов {@link AccountDto}
     */
    @Override
    public List<AccountDto> getAccountsByManagerId(Long managerId) {
        List<Account> accounts = accountRepository.findAccountsByManagerId(managerId);
        return accountMapper.accountsToAccountDtos(accounts);
    }

    /**
     * Обновляет поля существующего счета на основе данных из объекта AccountDto.
     * Если соответствующее поле в AccountDto не является null, то оно обновляется в существующем счете.
     *
     * @param accountDto      данные счета для обновления
     * @param existingAccount существующий счет, который нужно обновить
     */
    private void updateAccountFields(AccountDto accountDto, Account existingAccount) {
        updateFieldIfNotNull(accountDto.getName(), existingAccount::setName);
        updateFieldIfNotNull(accountDto.getBalance(), existingAccount::setBalance);
        updateFieldIfNotNull(accountDto.getStatusAccount(), existingAccount::setStatusAccount);
        updateFieldIfNotNull(accountDto.getAccountType(), existingAccount::setAccountType);
        updateFieldIfNotNull(accountDto.getCurrencyCode(), existingAccount::setCurrencyCode);
        updateFieldIfNotNull(accountDto.getCreatedAt(), existingAccount::setCreatedAt);
        updateFieldIfNotNull(accountDto.getUpdatedAt(), existingAccount::setUpdatedAt);
    }

    /**
     * Обновляет соглашения (договоры) у существующего счета на основе данных из объекта AccountDto.
     * Если в объекте AccountDto указаны обновленные соглашения, то они добавляются к существующему счету.
     * Если какое-либо соглашение не найдено в базе данных, генерируется исключение NotFoundEntityException.
     *
     * @param updatedAccountDto данные обновленного счета, включая обновленные соглашения
     * @param existingAccount   существующий счет, у которого нужно обновить соглашения
     * @throws NotFoundEntityException если какое-либо из соглашений не найдено в базе данных
     */
    private void updateAgreements(AccountDto updatedAccountDto, Account existingAccount) {
        Set<AgreementForAccountDto> updatedAgreements = updatedAccountDto.getAgreements();
        if (updatedAgreements != null && !updatedAgreements.isEmpty()) {
            Set<Agreement> existingAgreements = new HashSet<>();
            for (AgreementForAccountDto agreementDto : updatedAgreements) {
                Agreement existingAgreement = agreementRepository.findById(agreementDto.getId())
                        .orElseThrow(() -> new NotFoundEntityException("Договор с id: " + agreementDto.getId() + " не найден"));
                existingAgreements.add(existingAgreement);
            }
            existingAccount.setAgreements(existingAgreements);
        }
    }
}