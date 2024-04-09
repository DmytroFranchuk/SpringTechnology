package de.telran.SpringTechnologyBankApp.dtos.bank.client;

import de.telran.SpringTechnologyBankApp.dtos.bank.product.AgreementForProductDto;
import de.telran.SpringTechnologyBankApp.entities.bank.Manager;
import de.telran.SpringTechnologyBankApp.entities.enums.RoleType;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"email", "phone", "address"})
@ToString(of = {"email", "phone", "address"})
public class ClientDto {
    private Long id;
    private String taxNumber;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private String email;
    private String address;
    private String phone;
    private StatusType statusType;
    private RoleType roleType = RoleType.ROLE_CLIENT;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long managerId;
    private Set<AccountForClientDto> accounts = new HashSet<>();
}