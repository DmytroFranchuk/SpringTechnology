package de.telran.SpringTechnologyBankApp.repositories.history;

import de.telran.SpringTechnologyBankApp.entities.history.LoginUserHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginUserHistoryRepository extends JpaRepository<LoginUserHistory, Long> {
}
