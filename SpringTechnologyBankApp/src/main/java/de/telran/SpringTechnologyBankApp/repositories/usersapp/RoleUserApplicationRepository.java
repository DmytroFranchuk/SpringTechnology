package de.telran.SpringTechnologyBankApp.repositories.usersapp;

import de.telran.SpringTechnologyBankApp.entities.enums.RoleType;
import de.telran.SpringTechnologyBankApp.entities.usersapp.RoleUserApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleUserApplicationRepository extends JpaRepository<RoleUserApplication, Long> {
    Optional<RoleUserApplication> findByRoleType(RoleType roleType);
}
