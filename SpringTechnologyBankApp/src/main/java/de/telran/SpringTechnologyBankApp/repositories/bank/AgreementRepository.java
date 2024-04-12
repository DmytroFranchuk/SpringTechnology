package de.telran.SpringTechnologyBankApp.repositories.bank;

import de.telran.SpringTechnologyBankApp.entities.bank.Agreement;
import de.telran.SpringTechnologyBankApp.entities.enums.ProductType;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AgreementRepository extends JpaRepository<Agreement, Long> {
    List<Agreement> findAgreementsByStatusType(StatusType status);

    @Query("SELECT a FROM Agreement a JOIN a.product p WHERE p.productType = :productType AND a.statusType = 'ACTIVE'")
    List<Agreement> findAgreementsByProductType(@Param("productType") ProductType productType);
    // Выбрать договора со статусом ACTIVE, которые связаны с продуктами определенного типа

    @Query("SELECT a FROM Agreement a JOIN a.accounts acc WHERE acc.client.id = :clientId AND a.sum > :minSum")
    List<Agreement> findAgreementsByClientIdAndSumGreaterThan(
            @Param("clientId") Long clientId,
            @Param("minSum") BigDecimal minSum);
    // Выбрать договора, оформленные с клиентом у которого идентификатор ID равен clientId и отфильтровать по балансу счета

    @Query("SELECT a FROM Agreement a JOIN a.accounts acc JOIN acc.client c WHERE c.taxNumber = :taxNumber")
    List<Agreement> findAgreementsByClientTaxNumber(@Param("taxNumber") String taxNumber);
    // Выбрать договора, оформленные с клиентом у которого налоговый номер равен taxNumber

    @Query("SELECT a FROM Agreement a JOIN a.accounts acc JOIN acc.client c WHERE c.manager.id = :managerId")
    List<Agreement> findAgreementsByManagerId(@Param("managerId") Long managerId);
    // Выбрать договора, обслуживаемые менеджером у которого ID равен managerId

}
