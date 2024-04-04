package de.telran.SpringTechnologyBankApp.dtos.bank.manager;

import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"firstName", "lastName"})
@EqualsAndHashCode(of = {"firstName", "lastName"})
public class ClientForManagerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String taxNumber;
    private StatusType statusType;
}
