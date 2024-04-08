package de.telran.SpringTechnologyBankApp.repositories.bank;

import de.telran.SpringTechnologyBankApp.entities.bank.Product;
import de.telran.SpringTechnologyBankApp.entities.enums.CurrencyCode;
import de.telran.SpringTechnologyBankApp.entities.enums.ProductType;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByManagerId(Long id);
    List<Product> findAllByStatusType(StatusType status);
    List<Product> findAllByProductTypeAndCurrencyCode(ProductType type, CurrencyCode code);



}
