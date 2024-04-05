package de.telran.SpringTechnologyBankApp.dtos.bank.manager;

import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"firstName", "lastName"})
public class ManagerDtoForByCondition {
    private Long id;

    @NotNull
    @NotBlank
    @Pattern(regexp = "[a-zA-Z-]", message = "Manager first name contains invalid characters!")
    @Size(max = 100, message = "Managers first name can not be longer than 100 characters.")
    private String firstName;

    @NotNull
    @NotBlank
    @Pattern(regexp = "[a-zA-Z-]", message = "Manager last name contains invalid characters!")
    @Size(max = 200, message = "Managers last name can not be longer than 200 characters.")
    private String lastName;

    @NotNull
    @NotBlank
    @Email
    private String email;

    @Size(max = 200)
    private String description;

    @NotNull
    @NotBlank
    @Pattern(regexp = "[A-Z]+", message = "Manager status can be only ACTIVE, PENDING, REMOVED, BLOCKED or INACTIVE.")
    private StatusType statusType;

    @Override
    public String toString() {
        return "\nManagerDtoForStatus{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                ", statusType=" + statusType +
                '}';
    }
}
