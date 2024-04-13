package de.telran.SpringTechnologyBankApp.repositories.bank;

import de.telran.SpringTechnologyBankApp.entities.bank.Account;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByIdAndStatusAccount(Long id, StatusType status);

    List<Account> findAccountsByStatusAccount(StatusType status);

    List<Account> findAccountsByClientId(Long clientId);

    @Query("SELECT a FROM Account a INNER JOIN a.client c WHERE c.manager.id = :managerId")
    List<Account> findAccountsByManagerId(@Param("managerId") Long managerId);
}
