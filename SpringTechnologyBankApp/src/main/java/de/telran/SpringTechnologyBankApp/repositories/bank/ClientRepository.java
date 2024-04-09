package de.telran.SpringTechnologyBankApp.repositories.bank;

import de.telran.SpringTechnologyBankApp.entities.bank.Client;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findAllByManagerId(Long id);
    List<Client> findClientsByStatusType(StatusType status);

    @Query("SELECT c FROM Client c JOIN c.accounts a WHERE a.balance > :balance")
    List<Client> findAllClientsWithBalanceMoreThan(@Param("balance") BigDecimal balance);
}
