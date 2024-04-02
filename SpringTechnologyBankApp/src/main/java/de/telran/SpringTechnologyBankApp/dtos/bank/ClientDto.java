package de.telran.SpringTechnologyBankApp.dtos.bank;

import de.telran.SpringTechnologyBankApp.entities.enums.RoleType;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"taxNumber", "firstName", "phone"})
@ToString(of = {"firstName", "lastName"})
public class ClientDto {
    private Long id;
    private String taxNumber;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String address;
    private String phone;
    private StatusType statusType;
    private RoleType roleType = RoleType.ROLE_CLIENT;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}