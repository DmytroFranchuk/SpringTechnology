package de.telran.SpringTechnologyBankApp.services.bank.impl;

import de.telran.SpringTechnologyBankApp.dtos.bank.agreement.AgreementDto;
import de.telran.SpringTechnologyBankApp.entities.bank.Agreement;
import de.telran.SpringTechnologyBankApp.entities.enums.ProductType;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import de.telran.SpringTechnologyBankApp.exceptions.NotActiveEntityException;
import de.telran.SpringTechnologyBankApp.exceptions.NotExistEntityException;
import de.telran.SpringTechnologyBankApp.exceptions.NotFoundEntityException;
import de.telran.SpringTechnologyBankApp.exceptions.NotUpdatedEntityException;
import de.telran.SpringTechnologyBankApp.mappers.bank.AgreementMapper;
import de.telran.SpringTechnologyBankApp.repositories.bank.AgreementRepository;
import de.telran.SpringTechnologyBankApp.repositories.bank.ClientRepository;
import de.telran.SpringTechnologyBankApp.repositories.bank.ProductRepository;
import de.telran.SpringTechnologyBankApp.services.bank.interf.AgreementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static de.telran.SpringTechnologyBankApp.services.utilities.Utils.updateFieldIfNotNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgreementServiceImpl implements AgreementService {
    private final AgreementRepository agreementRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private final AgreementMapper agreementMapper;

    /**
     * Создает новый договор на основе данных из DTO.
     *
     * @param agreementDto DTO с данными нового договора
     * @return DTO с данными созданного договора
     * @throws DataIntegrityViolationException если нарушена целостность данных при сохранении
     * @throws RuntimeException              если не удалось создать договор по какой-либо причине
     */
    @Override
    public AgreementDto createAgreement(AgreementDto agreementDto) {
        try {
            Agreement agreement = agreementMapper.agreementDtoToAgreement(agreementDto);
            Agreement savedAgreement = agreementRepository.save(agreement);
            return agreementMapper.agreementToAgreementDto(savedAgreement);
        } catch (DataIntegrityViolationException exception) {
            String errorMessage = exception.getMessage();
            throw new DataIntegrityViolationException(errorMessage);
        } catch (Exception exception) {
            log.error("Не удалось создать ДОГОВОР", exception);
            throw new RuntimeException("Не удалось создать ДОГОВОР", exception);
        }
    }

    /**
     * Получает договор по его идентификатору.
     *
     * @param id идентификатор договора
     * @return DTO с данными найденного договора
     * @throws NotFoundEntityException если договор с указанным идентификатором не найден
     */
    @Override
    public AgreementDto getAgreementById(Long id) {
        return agreementRepository.findById(id).map(agreementMapper::agreementToAgreementDto)
                .orElseThrow(() -> new NotFoundEntityException("Не найден ДОГОВОР с id: " + id));
    }

    /**
     * Обновляет информацию о договоре по его идентификатору.
     *
     * @param id идентификатор договора
     * @param agreementDto новые данные договора
     * @return обновленный DTO с данными договора
     * @throws NotFoundEntityException если договор с указанным идентификатором не найден
     * @throws NotUpdatedEntityException если не удалось обновить договор
     */
    @Override
    public AgreementDto updateAgreementById(Long id, AgreementDto agreementDto) {
        Agreement existingAgreement = agreementRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("Договор с id: " + id + " не найден"));
        updateAgreementFields(agreementDto, existingAgreement);

        existingAgreement.setProduct(productRepository.getReferenceById(agreementDto.getProductId()));
        try {
            Agreement updatedAgreement = agreementRepository.save(existingAgreement);
            return agreementMapper.agreementToAgreementDto(updatedAgreement);
        } catch (Exception exception) {
            throw new NotUpdatedEntityException("Не удалось обновить договор с id: " + id);
        }
    }

    /**
     * Удаляет договор с указанным идентификатором {@code id}.
     * Извлекает существующий договор из репозитория по его идентификатору,
     * если договор не найден, выбрасывает исключение {@link NotFoundEntityException}.
     * Устанавливает статус договора на "Удален" и сохраняет изменения в репозитории.
     *
     * @param id идентификатор договора, который необходимо удалить
     * @throws NotFoundEntityException если договор с указанным идентификатором не найден
     */
    @Override
    public void deleteAgreementById(Long id) {
        Agreement existingAgreement = agreementRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("Договор с id: " + id + " не найден"));
        existingAgreement.setStatusType(StatusType.REMOVED);
        agreementRepository.save(existingAgreement);
    }

    /**
     * Получает список всех договоров с указанным типом статуса {@code status}.
     * Извлекает список договоров из репозитория по указанному типу статуса.
     * Преобразует каждый договор в объект {@link AgreementDto}, исключая информацию о счетах.
     *
     * @param status тип статуса, по которому необходимо получить договоры
     * @return список объектов {@link AgreementDto} с указанным типом статуса
     */
    @Override
    public List<AgreementDto> getAllAgreementsWhereStatusTypeIs(StatusType status) {
        List<Agreement> agreementsWithStatus = agreementRepository.findAgreementsByStatusType(status);
        return agreementsWithStatus.stream()
                .map(agreementMapper::agreementToAgreementDtoWithoutAccounts)
                .collect(Collectors.toList());
    }

    /**
     * Получает список всех договоров с указанным типом продукта {@code productType}.
     * Извлекает список договоров из репозитория по указанному типу продукта.
     * Преобразует каждый договор в объект {@link AgreementDto}, исключая информацию о счетах.
     *
     * @param productType тип продукта, по которому необходимо получить договоры
     * @return список объектов {@link AgreementDto} с указанным типом продукта
     */
    @Override
    public List<AgreementDto> findAgreementsByProductType(ProductType productType) {
        List<Agreement> agreements = agreementRepository.findAgreementsByProductType(productType);
        return agreements.stream()
                .map(agreementMapper::agreementToAgreementDtoWithoutAccounts)
                .collect(Collectors.toList());
    }

    /**
     * Обновляет поля объекта {@link Agreement} на основе данных из объекта {@link AgreementDto}.
     * Если соответствующее поле в объекте {@code agreementDto} не равно {@code null}, то оно обновляется в объекте {@code existingAgreement}.
     *
     * @param agreementDto      объект {@link AgreementDto}, содержащий обновленные данные
     * @param existingAgreement объект {@link Agreement}, который нужно обновить
     */
    private void updateAgreementFields(AgreementDto agreementDto, Agreement existingAgreement) {
        updateFieldIfNotNull(agreementDto.getInterestRate(), existingAgreement::setInterestRate);
        updateFieldIfNotNull(agreementDto.getCurrencyCode(), existingAgreement::setCurrencyCode);
        updateFieldIfNotNull(agreementDto.getStatusType(), existingAgreement::setStatusType);
        updateFieldIfNotNull(agreementDto.getSum(), existingAgreement::setSum);
    }
}
