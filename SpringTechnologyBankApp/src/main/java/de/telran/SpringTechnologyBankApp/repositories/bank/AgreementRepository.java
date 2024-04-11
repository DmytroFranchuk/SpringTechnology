package de.telran.SpringTechnologyBankApp.repositories.bank;

import de.telran.SpringTechnologyBankApp.entities.bank.Agreement;
import de.telran.SpringTechnologyBankApp.entities.enums.ProductType;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgreementRepository extends JpaRepository<Agreement, Long> {

    List<Agreement> findAgreementsByStatusType(StatusType status);

    @Query("SELECT a FROM Agreement a JOIN a.product p WHERE p.productType = :productType AND a.statusType = 'ACTIVE'")
    List<Agreement> findAgreementsByProductType(@Param("productType") ProductType productType);
    // Выбрать договора со статусом ACTIVE, которые связаны с продуктами определенного типа
}
