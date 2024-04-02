package de.telran.SpringTechnologyBankApp.repositories.usersapp;

import de.telran.SpringTechnologyBankApp.entities.usersapp.UserApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserApplicationRepository extends JpaRepository<UserApplication, Long> {
    Optional<UserApplication> findByLogin(String login);
}
