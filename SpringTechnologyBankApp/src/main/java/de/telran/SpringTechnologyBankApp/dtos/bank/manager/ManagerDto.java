package de.telran.SpringTechnologyBankApp.dtos.bank.manager;

import de.telran.SpringTechnologyBankApp.entities.enums.RoleType;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"firstName", "lastName"})
public class ManagerDto {

    private Long id;

    @NotNull
    @NotBlank
    @Pattern(regexp = "[a-zA-Z-]+", message = "Manager first name contains invalid characters!")
    @Size(max = 100, message = "Managers first name can not be longer than 100 characters.")
    private String firstName;

    @NotNull
    @NotBlank
    @Pattern(regexp = "[a-zA-Z-]+", message = "Manager last name contains invalid characters!")
    @Size(max = 200, message = "Managers last name can not be longer than 200 characters.")
    private String lastName;

    @NotNull
    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9]{3,}", message = "должно быть не менее 3 символов")
    private String login;

    @NotNull
    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9]{3,}")
    private String password;

    @NotNull
    @NotBlank
    @Email
    private String email;

    @Size(max = 200)
    private String description;

    private StatusType statusType;

    private RoleType roleType;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<ProductForManagerDto> products = new ArrayList<>();
    private List<ClientForManagerDto> clients = new ArrayList<>();

    @Override
    public String toString() {
        return "\nManagerDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                ", statusType=" + statusType +
                ", roleType=" + roleType +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", products=" + products +
                ", clients=" + clients +
                '}';
    }
}
