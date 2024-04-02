package de.telran.SpringTechnologyBankApp.dtos.bank;

import de.telran.SpringTechnologyBankApp.entities.enums.RoleType;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"firstName", "lastName", "description"})
@EqualsAndHashCode(of = {"firstName", "lastName", "statusType"})
public class ManagerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String description;
    private StatusType statusType;
    private RoleType roleType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
